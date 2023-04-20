package com.app.config.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class RocketMQConsumer {
    @Value("${apache.rocketmq.consumerGroup}")
    private String consumerGroup;

    @Value("${apache.rocketmq.namesrvAddr}")
    private String namesrvAddr;

    @Value("${apache.rocketmq.topic}")
    private String topic;

    @PostConstruct
    public void defaultMQPushConsumer() {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
        // 指定NameServer地址，多个地址以 ; 隔开
        consumer.setNamesrvAddr(namesrvAddr);
        try {
            // 设置consumer所订阅的Topic和Tag，*代表全部的Tag
            consumer.subscribe(topic, "*");
            // 设置消费策略，CONSUME_FROM_LAST_OFFSET 默认策略，从该队列最尾开始消费，跳过历史消息，CONSUME_FROM_FIRST_OFFSET 从队列最开始开始消费，即历史消息（还储存在broker的）全部消费一遍
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
            // MessageListenerOrderly 这个是有序的，MessageListenerConcurrently 这个是无序的,并行的方式处理，效率高很多
            consumer.registerMessageListener((MessageListenerConcurrently) (list, context) -> {
                try {
                    for (MessageExt messageExt : list) {
                        String messageBody = new String(messageExt.getBody(), RemotingHelper.DEFAULT_CHARSET);
                        log.info("收到消息：" + messageBody);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER; // 稍后再试
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS; // 消费成功
            });
            consumer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
