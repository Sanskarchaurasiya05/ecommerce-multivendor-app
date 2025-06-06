package com.sanskar.ecommerce02.repository;

import com.sanskar.ecommerce02.domain.AccountStatus;
import com.sanskar.ecommerce02.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRepository extends JpaRepository<Seller,Long> {
    Seller findByEmail(String email);
//    what entity we use here they must be present inside the entity
    List<Seller> findByAccountStatus(AccountStatus status);
}
