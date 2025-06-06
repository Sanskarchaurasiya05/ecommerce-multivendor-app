package com.sanskar.ecommerce02.repository;

import com.sanskar.ecommerce02.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    Order findOrderById(long id);

    List<Order> findByUserId(Long userId);

    List<Order> findBySellerId(Long sellerId);

}
