package com.example.shaverma_cloud.rest;

import com.example.shaverma_cloud.model.ShavermaOrder;
import com.example.shaverma_cloud.repository.OrderRepository;
import com.example.shaverma_cloud.service.OrderMessageService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderRepository orderRepository;
    private final OrderMessageService orderMessageService;
    public OrderController(OrderRepository orderRepository,OrderMessageService orderMessageService){
        this.orderRepository=orderRepository;
        this.orderMessageService=orderMessageService;
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ShavermaOrder post(@RequestBody ShavermaOrder shavermaOrder){
        orderMessageService.sendOrder(shavermaOrder);
        orderRepository.save(shavermaOrder);
        return shavermaOrder;
    }

}
