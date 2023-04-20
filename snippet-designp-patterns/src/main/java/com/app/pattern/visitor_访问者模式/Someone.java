package com.app.pattern.visitor_访问者模式;

/**
 * @version v1.0
 * @ClassName: Owner
 * @Description: 具体访问者角色类(其他人)
 * @Author: 
 */
public class Someone implements Person {

    public void feed(Cat cat) {
        System.out.println("其他人喂食猫");
    }

    public void feed(Dog dog) {
        System.out.println("其他人喂食狗");
    }
}
