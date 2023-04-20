package com.app.pattern.strategy_策略模式.demo2;

/**
 * @version v1.0
 * @ClassName: StrategyC
 * @Description: 具体策略类，封装算法
 * @Author: 
 */
public class StrategyC implements Strategy {

    public void show() {
        System.out.println("满1000元加一元换购任意200元以下商品");
    }
}
