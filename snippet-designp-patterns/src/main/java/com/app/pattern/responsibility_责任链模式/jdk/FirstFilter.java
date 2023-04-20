package com.app.pattern.responsibility_责任链模式.jdk;

/**
 * @version v1.0
 * @ClassName: FirstFilter
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 
 */
public class FirstFilter implements Filter {
    public void doFilter(Request req, Response res, FilterChain chain) {
        System.out.println("过滤器1 前置处理");

        // 先执行所有request再倒序执行所有response
        chain.doFilter(req, res);

        System.out.println("过滤器1 后置处理");
    }
}
