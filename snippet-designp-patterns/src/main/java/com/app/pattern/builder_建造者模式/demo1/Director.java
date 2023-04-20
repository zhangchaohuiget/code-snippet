package com.app.pattern.builder_建造者模式.demo1;

/**
 * @version v1.0
 * @ClassName: Director
 * @Description: 指挥者类
 * @Author:
 */
public class Director {

    //声明builder类型的变量
    private Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }

    //组装自行车的功能
    public Bike construct() {
        builder.buildFrame();
        builder.buildSeat();
        return builder.createBike();
    }
}
