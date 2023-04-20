package com.app.pattern.singleton_单例模式.demo1;

/**
 * @version v1.0
 * @ClassName: Client
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 
 */
public class Client {
    public static void main(String[] args) {
        //创建Singletion类的对象
        Singleton instance = Singleton.getInstance();

        Singleton instance1 = Singleton.getInstance();

        //判断获取到的两个是否是同一个对象
        System.out.println(instance == instance1);
    }
}
