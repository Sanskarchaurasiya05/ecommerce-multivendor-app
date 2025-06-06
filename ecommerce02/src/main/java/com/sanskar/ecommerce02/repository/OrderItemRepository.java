package com.sanskar.ecommerce02.repository;

import com.sanskar.ecommerce02.model.Order;
import com.sanskar.ecommerce02.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
