package com.app.pattern.factory_工厂模式.factory_method;

/**
 * @version v1.0
 * @ClassName: AmericanCoffeeFactory
 * @Description: 美式咖啡工厂对象，专门用来生产美式咖啡
 * @Author:
 */
public class AmericanCoffeeFactory implements CoffeeFactory {

    public Coffee createCoffee() {
        return new AmericanCoffee();
    }
}
