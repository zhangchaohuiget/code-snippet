package com.app.pattern.factory_工厂模式.before;

/**
 * @version v1.0
 * @ClassName: Coffee
 * @Description: 咖啡类
 * @Author:
 */
public abstract class Coffee {

    public abstract String getName();

    //加糖
    public void addsugar() {
        System.out.println("加糖");
    }

    //加奶
    public void addMilk() {
        System.out.println("加奶");
    }
}
