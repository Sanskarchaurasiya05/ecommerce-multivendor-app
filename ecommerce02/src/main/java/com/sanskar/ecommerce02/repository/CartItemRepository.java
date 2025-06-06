package com.sanskar.ecommerce02.repository;

import com.sanskar.ecommerce02.model.Cart;
import com.sanskar.ecommerce02.model.CartItem;
import com.sanskar.ecommerce02.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    CartItem findByCartAndProductAndSize(Cart cart , Product product , String size);

    CartItem findbyUserId(Long userId);
}
