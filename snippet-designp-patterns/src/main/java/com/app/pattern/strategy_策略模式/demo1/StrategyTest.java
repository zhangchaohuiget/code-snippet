package com.app.pattern.strategy_策略模式.demo1;

/**
 * 策略模式：定义算法族，使算法独立于客户，便于扩展和维护
 */
public class StrategyTest {
    public static void main(String[] args) {
        Context ctx = new Context(new StrategyImpl1());
        ctx.doMethod();
        ctx = new Context(new StrategyImpl2());
        ctx.doMethod();
        ctx = new Context(new StrategyImpl3());
        ctx.doMethod();
    }
}
