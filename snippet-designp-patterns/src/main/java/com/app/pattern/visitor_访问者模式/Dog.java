package com.app.pattern.visitor_访问者模式;

/**
 * @version v1.0
 * @ClassName: Cat
 * @Description: 具体元素角色类（宠物狗）
 * @Author: 
 */
public class Dog implements Animal {

    public void accept(Person person) {
        person.feed(this); //访问者给宠物猫喂食
        System.out.println("好好吃，汪汪汪。。。");
    }
}
