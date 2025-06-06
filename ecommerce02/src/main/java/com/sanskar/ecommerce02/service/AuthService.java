package com.sanskar.ecommerce02.service;

import com.sanskar.ecommerce02.domain.USER_ROLE;
import com.sanskar.ecommerce02.request.LoginRequest;
import com.sanskar.ecommerce02.response.AuthResponse;
import com.sanskar.ecommerce02.response.SignupRequest;
import org.springframework.context.annotation.Bean;

public interface AuthService {

    void sentLoginOtp(String email, USER_ROLE role) throws Exception;
    String createUser(SignupRequest req) throws Exception;
        AuthResponse sigin(LoginRequest req) throws Exception;
}
