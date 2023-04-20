package com.app.pattern.strategy_策略模式.demo1;

/**
 * 策略算法：算法调用
 */
public class Context {
    Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public void doMethod() {
        strategy.method();
    }
}
