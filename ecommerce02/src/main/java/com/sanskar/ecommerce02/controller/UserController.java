package com.sanskar.ecommerce02.controller;

import com.sanskar.ecommerce02.domain.USER_ROLE;
import com.sanskar.ecommerce02.model.User;
import com.sanskar.ecommerce02.response.AuthResponse;
import com.sanskar.ecommerce02.response.SignupRequest;
import com.sanskar.ecommerce02.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/profile")
    public ResponseEntity<User> createUserHandler(@RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);


        return ResponseEntity.ok(user);
    }

}
