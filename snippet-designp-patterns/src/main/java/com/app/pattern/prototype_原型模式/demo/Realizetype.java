package com.app.pattern.prototype_原型模式.demo;

/**
 * @version v1.0
 * @ClassName: Realizetype
 * @Description: TODO(一句话描述该类的功能)
 * @Author:
 */
public class Realizetype implements Cloneable {

    public Realizetype() {
        System.out.println("具体的原型对象创建完成！");
    }

    @Override
    public Realizetype clone() throws CloneNotSupportedException {
        System.out.println("具体原型复制成功！");

        return (Realizetype) super.clone();
    }
}
