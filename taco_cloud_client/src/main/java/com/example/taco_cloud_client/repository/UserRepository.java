package com.example.taco_cloud_client.repository;

import com.example.taco_cloud_client.dto.UserResponse;

public interface UserRepository {
    UserResponse getCurrentUser();
}
