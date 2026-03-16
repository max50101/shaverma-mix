package com.example.auth_server.service.impl;

import com.example.auth_server.config.RabbitConfig;
import com.example.auth_server.repository.entity.User;
import com.example.auth_server.service.UserMessageService;
import com.example.events.UserCreatedEvent;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.stereotype.Service;

@Service
public class RabbitUserMessageService implements UserMessageService {
    private RabbitTemplate rabbitTemplate;

    public RabbitUserMessageService(RabbitTemplate rabbitTemplate, Jackson2JsonMessageConverter jackson2JsonMessageConverter){
        this.rabbitTemplate=rabbitTemplate;
        this.rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
    }


    @Override
    public void send(User user) {
        UserCreatedEvent userCreatedEvent=new UserCreatedEvent(user.getId(),user.getUsername());
        rabbitTemplate.convertAndSend(RabbitConfig.ORDER_EXCHANGE, RabbitConfig.ORDER_ROUTING_KEY, userCreatedEvent, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                MessageProperties messageProperties=message.getMessageProperties();
                messageProperties.setHeader("X_USER_SOURSE","WEB");
                return message;
            }
        });
    }
}
