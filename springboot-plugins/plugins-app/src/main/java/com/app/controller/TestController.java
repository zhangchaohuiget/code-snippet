package com.app.controller;

import com.app.loader.HotClassLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;

/**
 * test
 *
 * @author ch
 * @date 2023/11/21 14:49
 */
@RestController
public class TestController {

    @GetMapping("/one")
    public String one() throws Exception {
        HotClassLoader.loadJar("/Users/zhangzhaohui/Downloads/plugins/plugins-one.jar", null);
        HotClassLoader oneClassLoader = HotClassLoader.JAR_CLASS_LOADERS.get("/Users/zhangzhaohui/Downloads/plugins/plugins-one.jar");
        Class<?> oneLoadedClass = oneClassLoader.loadClass("com.app.MyStrategy");
        // 创建实例
        Object oneInstance = oneLoadedClass.getDeclaredConstructor().newInstance();
        // 获取方法
        Method oneMethod = oneLoadedClass.getMethod("downloadInfo");
        // 调用方法
        oneMethod.invoke(oneInstance);
        return "success";
    }

    @GetMapping("/two")
    public String two() throws Exception {
        HotClassLoader.loadJar("/Users/zhangzhaohui/Downloads/plugins/plugins-two.jar", null);
        HotClassLoader twoClassLoader = HotClassLoader.JAR_CLASS_LOADERS.get("/Users/zhangzhaohui/Downloads/plugins/plugins-two.jar");
        Class<?> twoLoadedClass = twoClassLoader.loadClass("com.app.MyStrategy");
        // 创建实例
        Object twoInstance = twoLoadedClass.getDeclaredConstructor().newInstance();
        // 获取方法
        Method twoMethod = twoLoadedClass.getMethod("downloadInfo");
        // 调用方法
        twoMethod.invoke(twoInstance);
        return "success";
    }

}
