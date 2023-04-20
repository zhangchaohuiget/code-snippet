package com.app.pattern.visitor_访问者模式;

/**
 * @version v1.0
 * @ClassName: Person
 * @Description: 抽象访问者角色类
 * @Author: 
 */
public interface Person {

    //喂食宠物狗
    void feed(Cat cat);
    //喂食宠物猫
    void feed(Dog dog);
}
