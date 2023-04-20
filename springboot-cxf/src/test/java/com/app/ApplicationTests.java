package com.app;

import cn.hutool.http.webservice.SoapClient;
import com.app.model.User;
import com.app.service.ICommonService;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.junit.Before;
import org.junit.Test;

public class ApplicationTests {

    /**
     * 接口地址
     */
    private String wsdlAddress;

    @Before
    public void prepare() {
        wsdlAddress = "http://localhost:8088/services/CommonService?wsdl";
    }

    /**
     * 方式1.代理类工厂的方式,需要拿到对方的接口
     */
    @Test
    public void cl1() {
        try {
            // 接口地址
            // 代理工厂
            JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
            // 设置代理地址
            jaxWsProxyFactoryBean.setAddress(wsdlAddress);
            // 设置接口类型
            jaxWsProxyFactoryBean.setServiceClass(ICommonService.class);
            // 创建一个代理接口实现
            ICommonService cs = (ICommonService) jaxWsProxyFactoryBean.create();
            // 数据准备
            String userName = "Leftso";
            // 调用代理接口的方法调用并返回结果
            String result = cs.sayHello(userName);
            System.out.println("返回结果:" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 方式2. 动态调用方式
     */
    @Test
    public void cl2() {
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(wsdlAddress);
        // 需要密码的情况需要加上用户名和密码
        // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME, PASS_WORD));
        Object[] objects;
        try {
            // invoke("方法名",参数1,参数2,参数3....);
            objects = client.invoke("sayHello", "Leftso");
            System.out.println("返回类型：" + objects[0].getClass());
            System.out.println("返回数据:" + objects[0]);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 方式3. 动态调用方式，返回对象User
     */
    @Test
    public void cl3() {
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(wsdlAddress);
        Object[] objects;
        try {
            // invoke("方法名",参数1,参数2,参数3....);
            objects = client.invoke("getUser", "张三");
            System.out.println("返回类型：" + objects[0].getClass());
            System.out.println("返回数据:" + objects[0]);
            User user = (User) objects[0];
            System.out.println("返回对象User.name=" + user.getName());
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 方式4. hutool webservice工具类调用
     */
    @Test
    public void cl4() {
        // 新建客户端
        SoapClient client = SoapClient.create(wsdlAddress)
                // 设置要请求的方法，此接口方法前缀为web，传入对应的命名空间
                .setMethod("tns:getUser", "http://model.app.com/")
                // 设置参数，此处自动添加方法的前缀：web
                .setParam("userName", "zs");
        // 发送请求，参数true表示返回一个格式化后的XML内容
        // 返回内容为XML字符串，可以配合XmlUtil解析这个响应
        System.out.println(client.send(true));
    }

    // /**
    //  * 方式4. 客户端代码生成方式
    //  */
    // @Test
    // public void cl4() {
    //     CommonService_Service c = new CommonService_Service();
    //     com.xncoding.webservice.client.User user = c.getCommonServiceImplPort().getUser("Tom");
    //     assertThat(user.getName(), is("Tom"));
    // }

}
