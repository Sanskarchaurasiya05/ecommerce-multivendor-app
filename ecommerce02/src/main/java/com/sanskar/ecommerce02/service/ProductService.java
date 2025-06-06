package com.sanskar.ecommerce02.service;

import com.sanskar.ecommerce02.exception.ProductException;
import com.sanskar.ecommerce02.model.Product;
import com.sanskar.ecommerce02.model.Seller;
import com.sanskar.ecommerce02.request.CreateProductRequest;
import org.springframework.data.domain.Page;


import java.util.List;

public interface ProductService {

    public Product createProduct(CreateProductRequest req , Seller seller);
    public void deleteProduct(Long productId) throws ProductException;
    public Product updateProduct(Long ProductId , Product product);
    List<Product> searchProducts(String query);

    public Page<Product> getAllProducts(
            String category,
            String brand,
            String colors,
            String sizes,
            Integer minPrice,
            Integer maxPrice,
            Integer minDiscount,
            String stock,
            String sort,
            Integer pagenumber
    );

    List<Product> getProductBySellerId(Long sellerId);

    Product findProductById(Long productId) throws ProductException;
}
