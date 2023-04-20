package com.app.config.newRocketMq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(topic = "${rocketmq.producer.topic}", consumerGroup = "${rocketmq.producer.group}", accessKey = "admin", secretKey = "admin")
public class SpringConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String msg) {
        log.info("收到消息:" + msg);
    }
}