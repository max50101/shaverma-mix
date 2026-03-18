package com.example.taco_cloud_client.repository.impl;

import com.example.taco_cloud_client.dto.UserResponse;
import com.example.taco_cloud_client.repository.UserRepository;
import org.springframework.web.client.RestTemplate;

public class RestUserRepository implements UserRepository {
    private RestTemplate restTemplate;
    private String apiBaseUrl;

    public RestUserRepository(RestTemplate restTemplate,String apiBaseUrl){
        this.restTemplate=restTemplate;
        this.apiBaseUrl=apiBaseUrl;
    }
    @Override
    public UserResponse getCurrentUser() {
        return restTemplate.getForObject(apiBaseUrl+"/api/v1/users/me",UserResponse.class);
    }
}
