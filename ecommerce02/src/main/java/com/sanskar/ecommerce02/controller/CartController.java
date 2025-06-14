package com.sanskar.ecommerce02.controller;

import com.sanskar.ecommerce02.exception.ProductException;
import com.sanskar.ecommerce02.model.Cart;
import com.sanskar.ecommerce02.model.CartItem;
import com.sanskar.ecommerce02.model.Product;
import com.sanskar.ecommerce02.model.User;
import com.sanskar.ecommerce02.request.AddItemRequest;
import com.sanskar.ecommerce02.response.ApiResponse;
import com.sanskar.ecommerce02.service.CartItemService;
import com.sanskar.ecommerce02.service.CartService;
import com.sanskar.ecommerce02.service.ProductService;
import com.sanskar.ecommerce02.service.UserService;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {


    private final CartService cartService;
    private final CartItemService cartItemService;
    private final UserService userService;
    private final ProductService productService;



    @GetMapping
    public ResponseEntity<Cart> findUserCartHandler(@RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        Cart cart = cartService.findUserCart(user);

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/add")
    public ResponseEntity<CartItem> addItemToCart(@RequestBody AddItemRequest req,
                                                  @RequestHeader("Authorization") String jwt) throws  ProductException, Exception {

        User user=userService.findUserByJwtToken(jwt);
        Product product=productService.findProductById(req.getProductId());

        CartItem item = cartService.addCartItem(user,
                product,
                req.getSize(),
                req.getQuantity());

        ApiResponse res = new ApiResponse();
        res.setMessage("Item added To Cart Sucessfully");

        return new ResponseEntity<>(item,HttpStatus.ACCEPTED);

    }

    @DeleteMapping("/item/{cartItemId}")
    public ResponseEntity<ApiResponse>deleteCartItemHandler(
            @PathVariable Long cartItemId,
            @RequestHeader("Authorization")String jwt)
            throws  Exception {

        User user=userService.findUserByJwtToken(jwt);
        cartItemService.removeCartItem(user.getId(), cartItemId);

        ApiResponse res=new ApiResponse("Item Remove From Cart",true);

        return new ResponseEntity<ApiResponse>(res,HttpStatus.ACCEPTED);
    }

    @PutMapping("/item/{cartItemId}")
    public ResponseEntity<CartItem>updateCartItemHandler(
            @PathVariable Long cartItemId,
            @RequestBody CartItem cartItem,
            @RequestHeader("Authorization")String jwt)
            throws  Exception {

        User user=userService.findUserByJwtToken(jwt);

        CartItem updatedCartItem = null;
        if(cartItem.getQuantity()>0){
            updatedCartItem=cartItemService.updateCartItem(user.getId(),
                    cartItemId, cartItem);
        }
        return new ResponseEntity<>(updatedCartItem,HttpStatus.ACCEPTED);
    }
}
