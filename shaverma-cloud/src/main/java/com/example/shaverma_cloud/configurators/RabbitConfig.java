package com.example.shaverma_cloud.configurators;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String ORDER_QUEUE = "orders.queue";
    public static final String ORDER_EXCHANGE = "orders.exchange";
    public static final String ORDER_ROUTING_KEY = "orders.new";

    public static final String USERS_CREATED_QUEUE = "users.created.queue";
    public static final String USERS_EXCHANGE = "users.exchange";
    public static final String USERS_CREATED_ROUTING_KEY = "users.created";

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

    @Bean
    public Queue usersCreatedQueue() {
        return new Queue(USERS_CREATED_QUEUE, true);
    }

    @Bean
    public TopicExchange usersExchange() {
        return new TopicExchange(USERS_EXCHANGE, true, false);
    }

    @Bean
    public Binding usersCreatedBinding(Queue usersCreatedQueue, TopicExchange usersExchange) {
        return BindingBuilder
                .bind(usersCreatedQueue)
                .to(usersExchange)
                .with(USERS_CREATED_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}