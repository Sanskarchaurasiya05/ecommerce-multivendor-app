package com.sanskar.ecommerce02.controller;

import com.sanskar.ecommerce02.domain.OrderStatus;
import com.sanskar.ecommerce02.model.Order;
import com.sanskar.ecommerce02.model.Seller;
import com.sanskar.ecommerce02.service.OrderService;
import com.sanskar.ecommerce02.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seller/orders")
public class SellerOrderController {

    private final SellerService sellerService;
    private final OrderService orderService;

    @GetMapping()
    public ResponseEntity<List<Order>> getAllOrderHandler(
            @RequestHeader("Authorization") String jwt ) throws Exception{

        Seller seller = sellerService.getSellerProfile(jwt);
        List<Order> orders = orderService.sellersOrder(seller.getId());

        return new ResponseEntity<>(orders, HttpStatus.ACCEPTED);
    }

    @PatchMapping("/{orderId}/status/{orderStatus}")
    public ResponseEntity<Order> updateOrderHandler(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long orderId,
            @PathVariable OrderStatus orderStatus
            ) throws Exception {
        Order orders = orderService.updateOrderStatus(orderId,orderStatus);

        return new ResponseEntity<>(orders , HttpStatus.ACCEPTED);
    }

}
