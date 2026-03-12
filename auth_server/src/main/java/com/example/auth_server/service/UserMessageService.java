package com.example.auth_server.service;

import com.example.auth_server.repository.entity.User;

public interface UserMessageService {
    public void send(User user);
}
