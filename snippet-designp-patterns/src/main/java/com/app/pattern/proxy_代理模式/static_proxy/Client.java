package com.app.pattern.proxy_代理模式.static_proxy;

/**
 * @version v1.0
 * @ClassName: Client
 * @Description: TODO(一句话描述该类的功能)
 * @Author:
 */
public class Client {
    public static void main(String[] args) {
        //创建代售点类对象
        ProxyPoint proxyPoint = new ProxyPoint();
        //调用方法进行买票
        proxyPoint.sell();
    }
}
