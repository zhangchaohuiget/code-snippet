package com.app.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitQueueConfig {

    /* Direct demo */

    /**
     * 声明Direct交换机 支持持久化.
     */
    @Bean("directExchange")
    public Exchange directExchange() {
        return ExchangeBuilder.directExchange("DIRECT_EXCHANGE").durable(true).build();
    }

    /**
     * 声明一个队列 支持持久化.
     */
    @Bean("directQueue")
    public Queue directQueue() {
        return QueueBuilder.durable("DIRECT_QUEUE").build();
    }

    /**
     * 通过绑定键 将指定队列绑定到一个指定的交换机 .
     */
    @Bean
    public Binding directBinding(@Qualifier("directQueue") Queue queue,
                                 @Qualifier("directExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("DIRECT_ROUTING_KEY").noargs();
    }


    /* fanout demo */

    /**
     * 声明 fanout 交换机.
     */
    @Bean("fanoutExchange")
    public FanoutExchange fanoutExchange() {
        return (FanoutExchange) ExchangeBuilder.fanoutExchange("FANOUT_EXCHANGE").durable(true).build();
    }

    /**
     * Fanout queue A.
     */
    @Bean("fanoutQueueA")
    public Queue fanoutQueueA() {
        return QueueBuilder.durable("FANOUT_QUEUE_A").build();
    }

    /**
     * Fanout queue B .
     */
    @Bean("fanoutQueueB")
    public Queue fanoutQueueB() {
        return QueueBuilder.durable("FANOUT_QUEUE_B").build();
    }

    /**
     * 绑定队列A 到Fanout 交换机.
     */
    @Bean
    public Binding bindingA(@Qualifier("fanoutQueueA") Queue queue,
                            @Qualifier("fanoutExchange") FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }

    /**
     * 绑定队列B 到Fanout 交换机.
     */
    @Bean
    public Binding bindingB(@Qualifier("fanoutQueueB") Queue queue,
                            @Qualifier("fanoutExchange") FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }

    /* dead-letter demo */

    /**
     * 声明死信交换机.
     */
    @Bean("deadLetterExchange")
    public TopicExchange deadLetterExchange() {
        return new TopicExchange("DEAD_LETTER_EXCHANGE", true, false);
    }

    /**
     * 声明死信队列.
     */
    @Bean("deadLetterQueue")
    public Queue deadLetterQueue() {
        Map<String, Object> arguments = new HashMap<>();
        // 消息过期后将消息传递给xx交换机
        arguments.put("x-dead-letter-exchange", "DEAD_LETTER_HANDLER_EXCHANGE");
        arguments.put("x-dead-letter-routing-key", "DEAD_LETTER_HANDLER_ROUTING_KEY");
        // 给队列设置消息超时时间，此种方式不灵活
        // arguments.put("x-message-ttl", 10000);
        return new Queue("DEAD_LETTER_QUEUE", true, false, false, arguments);
    }

    /**
     * 将死信队列绑定至死信交换机
     */
    @Bean("deadLetterBinding")
    Binding deadLetterBinding(@Qualifier("deadLetterQueue") Queue queue, @Qualifier("deadLetterExchange") TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("DEAD_LETTER_ROUTING_KEY");
    }

    /**
     * 声明死信处理交换机.
     */
    @Bean("deadLetterHandlerExchange")
    public TopicExchange deadLetterHandlerExchange() {
        return new TopicExchange("DEAD_LETTER_HANDLER_EXCHANGE", true, false);
    }

    /**
     * 声明死信处理队列.
     */
    @Bean("deadLetterHandlerQueue")
    public Queue deadLetterHandlerQueue() {
        return new Queue("DEAD_LETTER_HANDLER_QUEUE", true, false, false);
    }

    /**
     * 将死信处理队列绑定至死信处理交换机
     */
    @Bean("deadLetterHandlerBinding")
    Binding deadLetterHandlerBinding(@Qualifier("deadLetterHandlerQueue") Queue expireOrderQueue, @Qualifier("deadLetterHandlerExchange") TopicExchange expireOrderExchange) {
        return BindingBuilder.bind(expireOrderQueue).to(expireOrderExchange).with("DEAD_LETTER_HANDLER_ROUTING_KEY");
    }
}
