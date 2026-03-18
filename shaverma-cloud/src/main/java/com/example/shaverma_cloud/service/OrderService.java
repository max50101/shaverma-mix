package com.example.shaverma_cloud.service;

import com.example.shaverma_cloud.model.ShavermaOrder;
import com.example.shaverma_cloud.model.User;
import com.example.shaverma_cloud.repository.OrderRepository;
import com.example.shaverma_cloud.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderMessageService orderMessageService;

    public OrderService(OrderRepository orderRepository,
                        UserRepository userRepository, OrderMessageService orderMessageService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderMessageService=orderMessageService;
    }

    public void createOrder(String username, ShavermaOrder order) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        updateUserFromOrder(user, order);
        userRepository.save(user);
        orderRepository.save(order);
        orderMessageService.sendOrder(order);

    }

    private void updateUserFromOrder(User user, ShavermaOrder order) {
        user.setFullname(order.getDeliveryName());
        user.setStreet(order.getDeliveryStreet());
        user.setCity(order.getDeliveryCity());
        user.setState(order.getDeliveryState());
        user.setZip(order.getDeliveryZip());
    }
}
