package com.example.shaverma_cloud.service;

import com.example.shaverma_cloud.configurators.RabbitConfig;
import com.example.shaverma_cloud.model.ShavermaOrder;
import org.ietf.jgss.MessageProp;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitOrderMessageService implements OrderMessageService{
    private RabbitTemplate rabbitTemplate;
    @Autowired
    public  RabbitOrderMessageService(RabbitTemplate rabbit, Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        this.rabbitTemplate = rabbit;
        this.rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
    }
    @Override
    public void sendOrder(ShavermaOrder order) {
        rabbitTemplate.convertAndSend(  RabbitConfig.ORDER_EXCHANGE,
                RabbitConfig.ORDER_ROUTING_KEY,order,
                new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                MessageProperties messageProperties=message.getMessageProperties();
                messageProperties.setHeader("X_ORDER_SOURSE","WEB");
                return message;
            }
        });
    }
}
