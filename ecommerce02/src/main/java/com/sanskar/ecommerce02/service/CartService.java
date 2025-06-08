package com.sanskar.ecommerce02.service;

import com.sanskar.ecommerce02.model.Cart;
import com.sanskar.ecommerce02.model.CartItem;
import com.sanskar.ecommerce02.model.Product;
import com.sanskar.ecommerce02.model.User;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service

public interface CartService {

    public CartItem addCartItem(
            User user,
            Product product,
            String size,
            int quantity
    );

    public Cart findUserCart(User user);



}
