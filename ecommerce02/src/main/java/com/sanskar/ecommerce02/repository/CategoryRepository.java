package com.sanskar.ecommerce02.repository;

import com.sanskar.ecommerce02.model.Category;
import com.sanskar.ecommerce02.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryId(String categoryId);
}
