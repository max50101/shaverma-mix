package com.example.shaverma_cloud.service;

import com.example.shaverma_cloud.model.ShavermaOrder;
import org.ietf.jgss.MessageProp;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;

public class RabbitOrderMessageService implements OrderMessageService{
    private RabbitTemplate rabbitTemplate;
    @Autowired
    public  RabbitOrderMessageService(RabbitTemplate rabbit) {
        this.rabbitTemplate = rabbit;
    }
    @Override
    public void sendOrder(ShavermaOrder order) {
        rabbitTemplate.convertAndSend(order, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                MessageProperties messageProperties=message.getMessageProperties();
                messageProperties.setHeader("X_ORDER_SOURSE","WEB");
                return message;
            }
        });
    }
}
