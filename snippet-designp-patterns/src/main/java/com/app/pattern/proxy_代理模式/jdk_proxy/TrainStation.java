package com.app.pattern.proxy_代理模式.jdk_proxy;

/**
 * @version v1.0
 * @ClassName: TrainStation
 * @Description: 火车站类
 * @Author:
 */
public class TrainStation implements SellTickets {

    public void sell() {
        System.out.println("火车站卖票");
    }
}
