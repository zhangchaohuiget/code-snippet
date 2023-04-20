package com.app.pattern.visitor_访问者模式;

/**
 * @version v1.0
 * @ClassName: Animal
 * @Description: 抽象元素角色类
 * @Author: 
 */
public interface Animal {

    //接受访问者访问的功能
    void accept(Person person);
}
