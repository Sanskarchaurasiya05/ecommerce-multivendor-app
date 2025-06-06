package com.sanskar.ecommerce02.service.impl;

import com.sanskar.ecommerce02.model.Cart;
import com.sanskar.ecommerce02.model.CartItem;
import com.sanskar.ecommerce02.model.Product;
import com.sanskar.ecommerce02.model.User;
import com.sanskar.ecommerce02.repository.CartItemRepository;
import com.sanskar.ecommerce02.repository.CartRepository;
import com.sanskar.ecommerce02.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

//    ------------------------------------------------------------------------------------------------
    @Override
    public CartItem addCartItem(User user, Product product, String size, int quantity) {
      Cart cart = findUserCart(user);

      CartItem isPresent=cartItemRepository.findByCartAndProductAndSize(cart,product,size);
      if(isPresent==null){
          CartItem cartItem = new CartItem();
          cartItem.setProduct(product);
          cartItem.setQuantity(quantity);
          cartItem.setUserId(user.getId());
          cartItem.setSize(size);

          int totalPrice = quantity*product.getSellingPrice();
            cartItem.setSellingPrice(totalPrice);
            cartItem.setMrpPrice(quantity* product.getMrpPrice());

          cart.getCartItems().add(cartItem);
          cartItem.setCart(cart);

          return cartItemRepository.save(cartItem);

      }

        return isPresent;
    }

    @Override
    public Cart findUserCart(User user) {
        Cart cart = cartRepository.findByUserId(user.getId());

        int totalPrice=0;
        int totlaDiscountedPrice=0;
        int totalItem=0;

        for(CartItem cartItem:cart.getCartItems()){
            totalPrice+=cartItem.getMrpPrice();
            totlaDiscountedPrice+=cartItem.getSellingPrice();
            totalItem+=cartItem.getQuantity();
        }

        cart.setTotalMrpPrice(totalPrice);
        cart.setTotalSellingPrice(totlaDiscountedPrice);
        cart.setDiscount(calculatedDiscountPercentage(totalPrice,totlaDiscountedPrice));
        cart.setTotalItem(totalItem);
        return cart;
    }

    private int calculatedDiscountPercentage(int mrpPrice, int sellingPrice) {

        if(mrpPrice<=0) return 0;

        double discount = mrpPrice-sellingPrice;
        double discountPercentage = (discount/mrpPrice)*100;

        return (int)discountPercentage;
    }
}
