package com.sanskar.ecommerce02.service;

import com.sanskar.ecommerce02.domain.OrderStatus;
import com.sanskar.ecommerce02.model.*;

import java.util.List;
import java.util.Set;

public interface OrderService {
    Set<Order> createOrder(User user , Address shippingAddress , Cart cart) throws Exception;
    Order findOrderById(long id) throws Exception;
    List<Order> usersOrderHistory(Long userId);
    List<Order> sellersOrder(Long sellerId);
    Order updateOrderStatus(Long orderId , OrderStatus orderStatus) throws Exception;
    Order cancelOrder(Long orderId , User user) throws Exception;
    OrderItem getOrderItemById(Long id) throws Exception;


}
