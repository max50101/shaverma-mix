package com.example.auth_server.service.impl;

import com.example.auth_server.repository.entity.User;
import com.example.auth_server.service.UserMessageService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitUserMessageService implements UserMessageService {
    private RabbitTemplate rabbitTemplate;

    public RabbitUserMessageService(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate=rabbitTemplate;
    }


    @Override
    public void send(User user) {

    }
}
