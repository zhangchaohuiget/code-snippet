package com.app.pattern.interpreter_解释器模式;

/**
 * @version v1.0
 * @ClassName: AbstractExpression
 * @Description: 抽象表达式类
 * @Author:
 */
public abstract class AbstractExpression {

    public abstract int interpret(Context context);
}
