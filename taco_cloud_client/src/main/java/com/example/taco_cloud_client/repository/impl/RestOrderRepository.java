package com.example.taco_cloud_client.repository.impl;

import com.example.taco_cloud_client.model.ShavermaOrder;
import com.example.taco_cloud_client.repository.OrderRepository;
import org.springframework.web.client.RestTemplate;

public class RestOrderRepository implements OrderRepository {
    private RestTemplate rest;
    private String apiBaseUrl;
    public RestOrderRepository(RestTemplate restTemplate, String apiBaseUrl){
        this.rest=restTemplate;
        this.apiBaseUrl=apiBaseUrl;
    }

    @Override
    public ShavermaOrder setOrder(ShavermaOrder order) {
        rest.postForObject(apiBaseUrl+"/api/v1/orders",order, ShavermaOrder.class);
        return order;
    }
}
