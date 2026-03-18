package com.example.taco_cloud_client.controllers;


import com.example.taco_cloud_client.dto.UserResponse;
import com.example.taco_cloud_client.model.ShavermaOrder;
import com.example.taco_cloud_client.repository.OrderRepository;
import com.example.taco_cloud_client.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("shavermaOrder")
public class OrderController {
    private OrderRepository orderRepository;
    private UserRepository userRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository,UserRepository userRepository){
        this.orderRepository=orderRepository;
        this.userRepository=userRepository;
    }
    @GetMapping("current")
    public String orderForm(@ModelAttribute("shavermaOrder") ShavermaOrder order){
        UserResponse user=userRepository.getCurrentUser();
        if (user != null) {
            if (order.getDeliveryName() == null || order.getDeliveryName().isBlank()) {
                order.setDeliveryName(user.fullname());
            }
            if (order.getDeliveryStreet() == null || order.getDeliveryStreet().isBlank()) {
                order.setDeliveryStreet(user.street());
            }
            if (order.getDeliveryCity() == null || order.getDeliveryCity().isBlank()) {
                order.setDeliveryCity(user.city());
            }
            if (order.getDeliveryState() == null || order.getDeliveryState().isBlank()) {
                order.setDeliveryState(user.state());
            }
            if (order.getDeliveryZip() == null || order.getDeliveryZip().isBlank()) {
                order.setDeliveryZip(user.zip());
            }
        }
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid ShavermaOrder order, Errors errors, SessionStatus sessionStatus){
        if(errors.hasErrors()){
            return "orderForm";
        }
        orderRepository.setOrder(order);
        log.info("Order submitted: {}", order);
        sessionStatus.setComplete();
        return "redirect:/";
    }
}
