package com.sanskar.ecommerce02.repository;

import com.sanskar.ecommerce02.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController

public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);
}
