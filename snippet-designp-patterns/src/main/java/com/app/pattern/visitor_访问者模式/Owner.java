package com.app.pattern.visitor_访问者模式;

/**
 * @version v1.0
 * @ClassName: Owner
 * @Description: 具体访问者角色类(自己)
 * @Author: 
 */
public class Owner implements Person {

    public void feed(Cat cat) {
        System.out.println("主人喂食猫");
    }

    public void feed(Dog dog) {
        System.out.println("主人喂食狗");
    }
}
