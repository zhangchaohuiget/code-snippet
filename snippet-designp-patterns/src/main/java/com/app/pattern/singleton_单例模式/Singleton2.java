package com.app.pattern.singleton_单例模式;

/**
 * 饿汉
 * 单例模式实现基本条件：
 * 1.构造方法私有化
 * 2.公共的静态方法返回实例对像
 * 饿汉式
 */
public class Singleton2 {
    private static Singleton2 singleton = new Singleton2();

    private Singleton2() {

    }

    public static Singleton2 getInstance() {
        return singleton;
    }
}
