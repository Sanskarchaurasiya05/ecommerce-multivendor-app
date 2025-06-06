package com.sanskar.ecommerce02.service.impl;

import com.sanskar.ecommerce02.model.CartItem;
import com.sanskar.ecommerce02.model.User;
import com.sanskar.ecommerce02.repository.CartItemRepository;
import com.sanskar.ecommerce02.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;

//    ----------------------------------------------------------------
    @Override
    public CartItem updateCartItem(Long userId, Long cartItemId, CartItem cartItem) throws Exception {
        CartItem item = findByCartItemById(cartItemId);

        User cartItemUser = item.getCart().getUser();

        if(cartItemUser.getId().equals(userId)){
            item.setQuantity(cartItem.getQuantity());
            item.setMrpPrice(item.getQuantity()*item.getProduct().getMrpPrice());
            item.setSellingPrice(item.getQuantity()*item.getProduct().getSellingPrice());

            return cartItemRepository.save(item);
        }

       throw new Exception("you can't update this cart item");
    }

    @Override
    public void removeCartItem(Long userId, Long CartItemId) throws Exception {
        CartItem item = findByCartItemById(CartItemId);

        User cartItemUser = item.getCart().getUser();

        if(cartItemUser.getId().equals(userId)){
            cartItemRepository.delete(item);
        }

        else throw new Exception("you can't delte this item");

    }

    @Override
    public CartItem findByCartItemById(Long id){
        return cartItemRepository.findById(id).orElseThrow(()->
                new ExpressionException("cart not find with id :"+id));
    }
}
