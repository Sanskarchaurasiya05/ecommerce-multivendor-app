package com.sanskar.ecommerce02.controller;

import com.sanskar.ecommerce02.exception.ProductException;
import com.sanskar.ecommerce02.model.Product;
import com.sanskar.ecommerce02.model.Seller;
import com.sanskar.ecommerce02.request.CreateProductRequest;
import com.sanskar.ecommerce02.service.ProductService;
import com.sanskar.ecommerce02.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sellers/products")
public class SellerProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private SellerService sellerService;



//    --------------------------------------------------------------------------------------------------
    @GetMapping()
    public ResponseEntity<List<Product>> getProductBySellerId(
            @RequestHeader("Authorization") String jwt) throws Exception {
        Seller seller = sellerService.getSellerProfile(jwt);
        List<Product> products = productService.getProductBySellerId(seller.getId());
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

//    ---------------------------------------------------------------------------------------------------

    @PostMapping()
    public ResponseEntity<Product> createProduct(
            @RequestBody CreateProductRequest request, @RequestHeader("Authorization")String jwt) throws Exception {

        Seller seller = sellerService.getSellerProfile(jwt);
        Product product1 = productService.createProduct(request , seller);
        return new ResponseEntity<>(product1,HttpStatus.CREATED);
    }

//    ------------------------------------------------------------------------------------------------

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId)  {
        try {
            productService.deleteProduct(productId);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(ProductException e){
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId ,
                                                   @RequestBody Product product){
        Product updateproduct = productService.updateProduct(productId , product);
        return new ResponseEntity<>(updateproduct,HttpStatus.OK);
    }

}
