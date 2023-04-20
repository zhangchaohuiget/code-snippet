package com.app.pattern.prototype_原型模式.test;

/**
 * @version v1.0
 * @ClassName: Student
 * @Description: TODO(一句话描述该类的功能)
 * @Author:
 */
public class Student {

    //学生的姓名
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }
}
