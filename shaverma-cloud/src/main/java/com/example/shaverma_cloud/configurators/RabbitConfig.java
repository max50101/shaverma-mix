package com.example.shaverma_cloud.configurators;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String ORDER_QUEUE = "orders.queue";
    public static final String ORDER_EXCHANGE = "orders.exchange";
    public static final String ORDER_ROUTING_KEY = "orders.new";

    @Bean
    public Queue orderQueue() {
        return new Queue(ORDER_QUEUE, true);
    }

    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(ORDER_EXCHANGE, true, false);
    }

    @Bean
    public Binding orderBinding(Queue orderQueue, TopicExchange orderExchange) {
        return BindingBuilder
                .bind(orderQueue)
                .to(orderExchange)
                .with(ORDER_ROUTING_KEY);
    }
}