package com.app.pattern.visitor_访问者模式;

/**
 * @version v1.0
 * @ClassName: Cat
 * @Description: 具体元素角色类（宠物猫）
 * @Author: 
 */
public class Cat implements Animal {

    public void accept(Person person) {
        person.feed(this); //访问者给宠物猫喂食
        System.out.println("好好吃，喵喵喵。。。");
    }
}
