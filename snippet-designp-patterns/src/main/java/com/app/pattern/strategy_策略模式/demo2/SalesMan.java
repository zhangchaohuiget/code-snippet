package com.app.pattern.strategy_策略模式.demo2;

/**
 * @version v1.0
 * @ClassName: SalesMan
 * @Description: 促销员(环境类)
 * @Author: 
 */
public class SalesMan {

    //聚合策略类对象
    private Strategy strategy;

    public SalesMan(Strategy strategy) {
        this.strategy = strategy;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    //由促销员展示促销活动给用户
    public void salesManShow() {
        strategy.show();
    }
}
