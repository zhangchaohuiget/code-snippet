package com.app.pattern.flyweight_享元模式.jdk;

/**
 * @version v1.0
 * @ClassName: Demo
 * @Description: TODO(一句话描述该类的功能)
 * @Author:
 */
public class Demo {
    public static void main(String[] args) {
        Integer i1 = 127;
        Integer i2 = 127;

        System.out.println("i1和i2对象是否是同一个对象？" + (i1 == i2));

        Integer i3 = 128;
        Integer i4 = 128;

        System.out.println("i3和i4对象是否是同一个对象？" + (i3 == i4));
    }
}
