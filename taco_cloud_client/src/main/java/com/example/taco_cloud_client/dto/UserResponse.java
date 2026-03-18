package com.example.taco_cloud_client.dto;

public record UserResponse(  String username,
                             String fullname,
                             String street,
                             String city,
                             String state,
                             String zip,
                             String phoneNumber){
}
