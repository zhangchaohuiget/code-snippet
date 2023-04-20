package com.app.pattern.decorator_装饰者模式;

/**
 * @version v1.0
 * @ClassName: FriedNoodles
 * @Description: 炒面(具体的构件角色)
 * @Author:
 */
public class FriedNoodles extends FastFood {

    public FriedNoodles() {
        super(12,"炒面");
    }

    public float cost() {
        return getPrice();
    }
}
