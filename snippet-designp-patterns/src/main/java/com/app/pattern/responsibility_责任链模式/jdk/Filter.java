package com.app.pattern.responsibility_责任链模式.jdk;

/**
 * @version v1.0
 * @ClassName: Filter
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 
 */
public interface Filter {
    public void doFilter(Request req,Response res,FilterChain c);
}
