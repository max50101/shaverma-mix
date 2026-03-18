package com.example.shaverma_cloud.messaging;

import com.example.events.UserCreatedEvent;
import com.example.shaverma_cloud.configurators.RabbitConfig;
import com.example.shaverma_cloud.model.User;
import com.example.shaverma_cloud.repository.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class UserListener {
    private UserRepository userRepository;
    public UserListener(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    @RabbitListener(queues = RabbitConfig.USERS_CREATED_QUEUE)
    public void receiveUserCreated(UserCreatedEvent userCreatedEvent){
        if (userRepository.existsByUsername(userCreatedEvent.getUsername())) {
            return;
        }

        User user = new User();
        user.setUsername(userCreatedEvent.getUsername());
        user.setId(userCreatedEvent.getUserId());
        userRepository.save(user);
    }

}
