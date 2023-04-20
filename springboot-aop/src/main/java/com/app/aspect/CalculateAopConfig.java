package com.app.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 使用配置方式实现aop
 * 为CalculateService中的每个计算方法加上计算执行时间的增强
 *
 * @author zch
 * @date 2023/4/15 17:38
 */
@Aspect
@Component
public class CalculateAopConfig {

    /**
     * 环绕通知
     * com.app.service.CalculateService.*(..)) 表示CalculateService中的所有方法都执行此环绕通知
     *
     * @param pJoinPoint 连接点：用于表示程序在特定连接点处的当前执行状态,它包含诸如目标对象、正在执行的方法以及传递给方法的参数等信息。
     */
    @Around(value = "execution(* com.app.service.CalculateService.*(..))")
    public int myAround(ProceedingJoinPoint pJoinPoint) {
        String name = pJoinPoint.getSignature().getName();
        Object[] args = pJoinPoint.getArgs();
        int result = 0;
        try {
            System.out.println("执行前置通知" + name + "执行了,传入的参数是" + Arrays.asList(args));
            long start = System.currentTimeMillis();
            // 可以理解为执行目标方法
            result = (int) pJoinPoint.proceed();
            long end = System.currentTimeMillis();
            System.out.println("后置通知执行了，返回值是" + result);
            System.out.println("执行时间：" + (end - start));
        } catch (Throwable e) {
            System.out.println("异常通知执行了" + name + "出异常了,异常是" + e);
            e.printStackTrace();
        } finally {
            System.out.println("后置通知执行了" + name + "结束了");
        }
        return result;
    }

}