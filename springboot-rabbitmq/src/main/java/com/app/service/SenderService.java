package com.app.service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * 消息发送服务
 */
@Service
public class SenderService {
    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 测试广播模式.
     */
    public void broadcast(String msg) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("FANOUT_EXCHANGE", "", msg, correlationData);
    }

    /**
     * 测试Direct模式.
     */
    public void direct(String msg) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("DIRECT_EXCHANGE", "DIRECT_ROUTING_KEY", msg, correlationData);
    }

    /**
     * 测试deadLetter模式.
     */
    public void deadLetter(String msg) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        // 针对单个消息设置过期时间
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setExpiration(String.valueOf(10000));
        Message message = new Message(msg.getBytes(), messageProperties);
        rabbitTemplate.convertAndSend("DEAD_LETTER_EXCHANGE", "DEAD_LETTER_ROUTING_KEY", message, correlationData);
    }
}
