package com.example.shaverma_cloud.dto.response;

public record UserResponse(  String username,
         String fullname,
         String street,
         String city,
         String state,
         String zip,
         String phoneNumber){
}
