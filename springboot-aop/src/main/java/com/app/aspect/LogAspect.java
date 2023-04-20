package com.app.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 使用自定义注解方式实现aop
 * 为Controller日志做增强
 *
 * @author zch
 * @date 2023/4/15 17:56
 */
@Component
@Aspect
public class LogAspect {

    private final static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 连接点
     */
    @Pointcut(value = "@annotation(com.app.aspect.Log)")
    public void access() {

    }

    /**
     * 前置通知
     */
    @Before("access()")
    public void before(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        logger.info("请求: " + request.getRequestURL().toString() + "开始");
        logger.info("CLASS_METHOD : " + signature.getDeclaringTypeName() + "." + signature.getName());
    }

    /**
     * 后置通知
     */
    @After("access()")
    public void after(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        logger.info("请求: " + request.getRequestURL().toString() + "结束");
    }
}
