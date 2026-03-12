package com.example.shaverma_cloud.service;

import com.example.shaverma_cloud.model.ShavermaOrder;

public interface OrderMessageService {
    public void sendOrder(ShavermaOrder order);
}
