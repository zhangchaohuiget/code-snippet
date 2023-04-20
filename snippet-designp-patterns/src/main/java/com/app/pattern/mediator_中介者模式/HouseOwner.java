package com.app.pattern.mediator_中介者模式;

/**
 * @version v1.0
 * @ClassName: HouseOwner
 * @Description: 具体的同事角色类
 * @Author: 
 */
public class HouseOwner extends Person {

    public HouseOwner(String name, Mediator mediator) {
        super(name, mediator);
    }

    //和中介联系（沟通）
    public void constact(String message) {
        mediator.constact(message,this);
    }

    //获取信息
    public void getMessage(String message) {
        System.out.println("房主" + name + "获取到的信息是：" + message);
    }
}
