package com.app.loader;

import com.app.util.SpringBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * 自定义类加载器，主要用来加载指定目录下的所有以.jar结尾的文件
 *
 * @author zhangch
 * @date 2023/11/21 11:44
 */
@Slf4j
public class HotClassLoader extends URLClassLoader {

    /**
     * 设定插件默认放置的路径
     */
    private static final String PLUGINS_DIR = "/Users/zhangzhaohui/Downloads/plugins";
    /**
     * jar更新时间键值对，通过value来判断jar包是否被修改了
     */
    public static final ConcurrentHashMap<String, Long> JAR_UPDATE_TIME = new ConcurrentHashMap<>();
    /**
     * jar包对应的类加载器，即1个jar包对应1个类加载器，但是1个类加载器可以对应N个jar包
     */
    public static final ConcurrentHashMap<String, HotClassLoader> JAR_CLASS_LOADERS = new ConcurrentHashMap<>();
    /**
     * jar包中类的完全限定名键值对，即1个jar包包含N个class
     */
    public static final ConcurrentHashMap<String, List<String>> JAR_CLASS_NAME = new ConcurrentHashMap<>();

    public HotClassLoader(ClassLoader parent) {
        super(new URL[0], parent);
    }

    /**
     * 一次性加载plugins目录下的所有jar（这个可以放在定时扫描中，n秒执行一次）
     * 但是前提必须是jar包有更新，也就是第一次是全量加载，后面扫描只会基于更新的jar做热替换
     */
    public static void loadAllJar() throws Exception {
        File file = null;
        file = checkPluginDir();
        // 遍历目录下所有文件
        File[] files = file.listFiles();
        if (files == null || files.length == 0) {
            log.info("no plugins resource need loading...");
            return;
        }

        List<String> updatedJars = getUpdatedJars(files);
        if (updatedJars.size() == 0) {
            log.info("There are no Jars to update !");
            return;
        }

        /*
         * 如果本次扫描发现有jar包更新，则从ioc容器里取出新的classLoader实例以加载这些jar包中的class
         * 这个地方很巧妙。即同一批次更新的jar包会使用用同一个类加载器去加载，这就避免了类加载器不会平白无故的多出很多！
         * 为什么这里要重新加载呢？我们知道，判断类对象在JVM中是否独有一份并不取决于它的完全限定名，即com.app.xxx
         * 是唯一的，还取决于把它载入到JVM内存中的类加载器对象是不是同一个，也就是同样是User.class,我们可以让它在JVM中存在多份，
         * 这个只需要创建不同的用户自定义的类加载对象去loadClass即可实现，话又说回来，如果不是热更新class的需求，
         * 真实开发中我们肯定是不会那么干的！ 这里使用新的类加载对象去加载这一批更新的jar包的目的就是实现Class的热卸载和热替换。
         * 具体怎么做的，可以细看loadJar方法的实现，最好一边调试一边看，效果最佳！
         */
        HotClassLoader classLoader = SpringBeanUtils.getBean(HotClassLoader.class);
        for (String updatedJar : updatedJars) {
            loadJar(updatedJar, classLoader);
        }
    }

    /**
     * 判断插件目录是否存在，如果不存在，则创建
     *
     * @author zhangch
     * @date 2023/11/21 11:52
     */
    private static File checkPluginDir() {
        File file = new File(PLUGINS_DIR);
        if (!file.exists()) {
            boolean mkdirs = file.mkdirs();
        }
        return file;
    }

    /**
     * 获取需更新的jar文件
     *
     * @author zhangch
     * @date 2023/11/21 11:55
     */
    private static List<String> getUpdatedJars(File[] files) {
        List<String> updatedJars = new ArrayList<>();
        for (File childFile : files) {
            String name = childFile.getName();
            // 如果子文件对象是文件夹，则不处理
            if (childFile.isDirectory()) {
                log.warn("not support the folder of " + name);
                continue;
            }
            // 如果文件不以jar结尾，也不处理
            if (!name.endsWith(".jar")) {
                log.warn("not support the plugin file of " + name);
                continue;
            }
            // 构建jar类路径
            String jarPath = String.format("%s/%s", PLUGINS_DIR, name);
            long lastModifyTime = childFile.getAbsoluteFile().lastModified();
            if (Objects.equals(lastModifyTime, JAR_UPDATE_TIME.get(jarPath))) {
                continue;
            }
            // 将修改过的jar路径保存起来
            log.warn(String.format("%s changed, need to reload", jarPath));
            updatedJars.add(jarPath);
        }
        return updatedJars;
    }


    /**
     * 使用指定的类加载加载单个jar文件中的所有class文件到JVM中，同时向Spring IOC容器中注入BD
     *
     * @param jarPath     jar类路径
     * @param classLoader 类加载器
     */
    public static void loadJar(String jarPath, HotClassLoader classLoader) throws Exception {
        // 先尝试从jar更新时间map中取出jarPath的更新时间
        Long lastModifyTime = JAR_UPDATE_TIME.get(jarPath);
        // 如果等于0L,说明这个jar包还处于加载中，直接退出
        if (Objects.equals(lastModifyTime, 0L)) {
            log.warn("HotClassLoader.loadJar loading ,please not repeat the operation, jarPath = {}", jarPath);
            return;
        }

        // 拿到jar文件对象
        File file = new File(jarPath);
        // 为了保险，还是判断下jarPath（有可能是外部传进来的非法jarPath）是否存在
        if (!file.exists()) {
            log.warn("HotClassLoader.loadJar fail file not exist, jarPath = {}", jarPath);
            return;
        }

        // 获取真实物理jarPath文件的修改时间
        long currentJarModifyTime = file.getAbsoluteFile().lastModified();
        // 如果通过对比发现jar包没有做任何修改，则不予重新加载，退出
        if (Objects.equals(lastModifyTime, currentJarModifyTime)) {
            log.warn("HotClassLoader.loadJar current version has bean loaded , jarPath = {}", jarPath);
            return;
        }

        // 获取新的类加载器
        if (classLoader == null) {
            classLoader = SpringBeanUtils.getBean(HotClassLoader.class);
        }

        /*
         * 如果jar包做了修改，则进行卸载流程
         * 用户自定义类加载器加载出来的Class被JVM回收的三个苛刻条件分别是：
         * 1、Class对应的所有的实例在JVM中不存在，即需要手动设置clzInstance = null;
         * 2、加载该类的ClassLoader在JVM中不存在，即需要手动设置classLoader = null;
         * 3、Class对象没有在任何地方被引用，比如不能再使用反射API，即需要手动设置class = null;
         */
        if (JAR_UPDATE_TIME.containsKey(jarPath)) {
            unloadJar(jarPath);
        }

        try {
            classLoader.addURL(file.toURI().toURL());
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("通过url 添加 jar 失败");
        }

        // 保存或更新当前jarPath的类加载器
        JAR_CLASS_LOADERS.put(jarPath, classLoader);

//        // 开始（重新）加载前，初始化jarPth的更新时间为0
//        JAR_UPDATE_TIME.put(jarPath, 0L);
//
//        List<String> classNameList = injectedBeans(classLoader, file);
//
//        // 记录jarPath包含的所有的类
//        JAR_CLASS_NAME.put(jarPath, classNameList);
//
//        // 记录jarPath的更新时间
//        JAR_UPDATE_TIME.put(jarPath, currentJarModifyTime);
    }

    private static List<String> injectedBeans(HotClassLoader classLoader, File file) {
        List<String> classNameList = new ArrayList<>();
        // 遍历 jar 包中的类
        try (JarFile jarFile = new JarFile(file.getAbsolutePath())) {
            List<JarEntry> jarEntryList = jarFile.stream().sequential().collect(Collectors.toList());
            for (JarEntry jarEntry : jarEntryList) {
                String jarName = jarEntry.getName();
                if (!jarName.endsWith(".class")) {
                    continue;
                }
                // 类的完全限定名处理
                String className = jarName.replace(".class", "").replace("/", ".");
                boolean beanExist = SpringBeanUtils.contains(className);
                // 如果存在，更新
                if (beanExist) {
                    SpringBeanUtils.removeBean(className);
                }
                // 使用指定的类加载器加载该类
                Class<?> clz = classLoader.loadClass(className, false);

                /*
                 * 这个地方要反射一下，判断下，clazz上是否有注解 @PluginOn
                 * 只有标识了我们自定义的插件注解，才能作为bean添加到spring ioc容器里
                 */
                boolean withPluginOn =
                        AnnotationUtils.findAnnotation(clz, Component.class) != null;
                if (withPluginOn) {
                    // 将class包装成BeanDefinition注册到Spring容器中
                    SpringBeanUtils.registerBean(className, clz);
                    /*
                     * 动态替换bean，这个地方从常用的角度来看，我们只需处理@Controller类，
                     * 给@AutoWired修饰的类字段做替换即可
                     */
                    doAutowired(className, clz);
                }
                classNameList.add(className);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalArgumentException("jar包解析失败");
        }
        return classNameList;
    }

    /**
     * 卸载指定jar
     */
    private static void unloadJar(String jarPath) throws Exception {
        // 校验文件是否存在
        File file = ResourceUtils.getFile(jarPath);
        if (!file.exists()) {
            log.warn("HotClassLoader.loadJar fail file not exist, jarPath = {}", jarPath);
            return;
        }
        List<String> classNameList = JAR_CLASS_NAME.get(jarPath);
        if (CollectionUtils.isEmpty(classNameList)) {
            log.warn("HotClassLoader.loadJar fail,the jar no class, jarPath = {}", jarPath);
            return;
        }

        HotClassLoader oldClassLoader = JAR_CLASS_LOADERS.get(jarPath);
        // 遍历移除spring中对应的bean,移除引用
        for (String className : classNameList) {
            boolean beanExist = SpringBeanUtils.contains(className);
            if (beanExist) {
                // 把旧的类实例移除，切断对象引用
                SpringBeanUtils.removeBean(className);
            }
            // 把旧的类加载器加载的Class对象置为null
            Class<?> oldClz = oldClassLoader.loadClass(className, false);
            oldClz = null;
        }
        // 移除jarPath
        JAR_UPDATE_TIME.remove(jarPath);
        // 关闭类加载，然后切断引用
        if (oldClassLoader != null) {
            oldClassLoader.close();
            oldClassLoader = null;
        }
    }

    /**
     * 处理bean的自动注入（手动）
     * 这一块代码逻辑稍显复杂，但是还好，有spring源码基础的小伙伴一定不陌生！
     * 这块的逻辑思路主要是借鉴了nacos的源码：
     * nacos不仅是配置中心还是服务注册与发现中心，其作为配置中心的时候，我们知道，
     * 项目中的Bean类中只要使用了@NacosValue注解去修饰属性字段，那么，一旦我们在
     * nacos的web端修改了指定配置属性字段的值并保存后，那么项目端无需重启，
     * 就可以获取到最新的配置值，它是怎么做到的呢？ 首先抛开tcp连接不说，就说更新这块，
     * 那必然是先通过网络请求拿到nacos数据库中最新的配置值（值改变了会触发回调），然后
     * 找到这个字段所在的bean，然后再定位到bean实例的属性字段，然后通过反射set新值，
     * 也就是内存中保存的是旧值，然后运维或开发人员在nacos端修改了某项配置值，
     * 然后会通知App端进行值更新，App端获取到新的值后，会找到该值所在的beans，
     * 然后通过反射修改这些beans中的这个字段的值，修改成功后，内存中的旧值就被“热替换”了！
     */
    private static void doAutowired(String className, Class clz) {
        Map<String, Object> beanMap = SpringBeanUtils.getBeanMap(RestController.class);
        if (beanMap.size() == 0) {
            return;
        }
        // 拿到clz的接口
        Class[] clzInterfaces = clz.getInterfaces();
        beanMap.forEach((k, v) -> {
            Class<?> cz = v.getClass();
            // 拿到class所有的字段（private，protected，public，但不包括父类的）
            Field[] declaredFields = cz.getDeclaredFields();
            // 遍历字段，只处理@Autowired注解的字段值的注入
            for (Field declaredField : declaredFields) {
                if (!declaredField.isAnnotationPresent(Autowired.class)) {
                    return;
                }
                // 推断下字段类型是否是接口（如果是接口的话，注入的条件稍显"复杂"些）
                boolean bInterface = declaredField.getType().isInterface();
                // 拿到字段的类型完全限定名
                String fieldTypeName = declaredField.getType().getName();

                // 设置字段可以被修改，这一版本，先不考虑多态bean的情况，下一个版本完善时再考虑
                declaredField.setAccessible(true);
                try {
                    // 如果字段的类型非接口并且字段的类的完全限定名就等于clz的名，那就直接setter设置
                    if (!bInterface && fieldTypeName.equals(clz.getName())) {
                        declaredField.set(v, SpringBeanUtils.getBean(className, clz));
                    }
                    // 如果字段类型是接口，还得判断下clz是不是实现了某些接口，如果是，得判断两边接口类型是否一致才能注入值
                    if (bInterface) {
                        for (Class inter : clzInterfaces) {
                            if (fieldTypeName == inter.getName()) {
                                declaredField.set(v, SpringBeanUtils.getBean(className, clz));
                                break;
                            }
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        if (name.startsWith("java.")) {
            return ClassLoader.getSystemClassLoader().loadClass(name);
        }
        Class<?> clazz = findLoadedClass(name);
        if (clazz != null) {
            if (resolve) {
                return loadClass(name);
            }
            return clazz;
        }
        return super.loadClass(name, resolve);
    }

}
