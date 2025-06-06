package com.sanskar.ecommerce02.service;

import com.sanskar.ecommerce02.model.CartItem;

public interface CartItemService {

    CartItem updateCartItem(Long userId , Long cartItemId , CartItem cartItem) throws Exception;
    void removeCartItem(Long userId , Long CartItemId) throws Exception;
    CartItem findByCartItemById(Long id);
}
