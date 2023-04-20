package com.app.config.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RocketMQProducer {
    @Value("${apache.rocketmq.consumerGroup}")
    private String consumerGroup;

    @Value("${apache.rocketmq.namesrvAddr}")
    private String namesrvAddr;

    private DefaultMQProducer producer;

    public DefaultMQProducer getProducter() {
        return producer;
    }

    @PostConstruct
    public void init() {
        producer = new DefaultMQProducer(consumerGroup);
        producer.setNamesrvAddr(namesrvAddr);
        producer.setVipChannelEnabled(false);
        try {
            producer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}