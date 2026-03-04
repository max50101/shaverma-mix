package com.example.shaverma_cloud.repository;

import com.example.shaverma_cloud.model.ShavermaOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<ShavermaOrder,Long> {
    List<ShavermaOrder> findByDeliveryZip(String deliveryZip);
}






