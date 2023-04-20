package com.app.pattern.strategy_策略模式.demo2;

/**
 * @version v1.0
 * @ClassName: StrategyB
 * @Description: 具体策略类，封装算法
 * @Author: 
 */
public class StrategyB implements Strategy {

    public void show() {
        System.out.println("满200元减50元");
    }
}
