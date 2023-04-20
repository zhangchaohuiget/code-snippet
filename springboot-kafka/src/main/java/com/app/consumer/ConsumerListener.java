package com.app.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConsumerListener {

    @KafkaListener(topics = "${spring.kafka.topic}", groupId = "${spring.kafka.group-id}")
    public void onMessage(String message) {
        log.info("收到消息：[{}]", message);
    }

}