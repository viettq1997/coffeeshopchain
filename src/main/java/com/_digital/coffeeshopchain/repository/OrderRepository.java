package com._digital.coffeeshopchain.repository;

import com._digital.coffeeshopchain.domain.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, String> {
    List<OrderEntity> findByShopIdAndOrderStatus(String shopId, String orderStatus);
}
