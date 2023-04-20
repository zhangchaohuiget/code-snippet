package com.app.pattern.observer_观察者模式;

/**
 * @version v1.0
 * @ClassName: WeiXinUser
 * @Description: 具体的观察者角色类
 * @Author: 
 */
public class WeiXinUser implements Observer {

    private String name;

    public WeiXinUser(String name) {
        this.name = name;
    }

    public void update(String message) {
        System.out.println(name + "-" + message);
    }
}
