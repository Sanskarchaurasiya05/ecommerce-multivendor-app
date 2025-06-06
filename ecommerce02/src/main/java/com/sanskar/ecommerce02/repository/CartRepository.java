package com.sanskar.ecommerce02.repository;

import com.sanskar.ecommerce02.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {

    Cart findByUserId(Long id);

}
