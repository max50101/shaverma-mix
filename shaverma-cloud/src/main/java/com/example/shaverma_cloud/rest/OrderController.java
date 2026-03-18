package com.example.shaverma_cloud.rest;

import com.example.shaverma_cloud.model.ShavermaOrder;
import com.example.shaverma_cloud.repository.OrderRepository;
import com.example.shaverma_cloud.repository.UserRepository;
import com.example.shaverma_cloud.service.OrderMessageService;
import com.example.shaverma_cloud.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    private final UserRepository userRepository;
    public OrderController(OrderService orderRepository, OrderMessageService orderMessageService, UserRepository userRepository){
        this.orderService=orderRepository;
        this.userRepository=userRepository;
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ShavermaOrder post(@AuthenticationPrincipal Jwt jwt, @RequestBody ShavermaOrder shavermaOrder){
        String username=jwt.getSubject();
        orderService.createOrder(username,shavermaOrder);
        return shavermaOrder;
    }

}
