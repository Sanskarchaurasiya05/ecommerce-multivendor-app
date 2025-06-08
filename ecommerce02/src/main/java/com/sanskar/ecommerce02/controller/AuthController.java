package com.sanskar.ecommerce02.controller;

import com.sanskar.ecommerce02.domain.USER_ROLE;
import com.sanskar.ecommerce02.model.User;
import com.sanskar.ecommerce02.model.VerificationCode;
import com.sanskar.ecommerce02.repository.UserRepository;
import com.sanskar.ecommerce02.request.LoginOtpRequest;
import com.sanskar.ecommerce02.request.LoginRequest;
import com.sanskar.ecommerce02.response.ApiResponse;
import com.sanskar.ecommerce02.response.AuthResponse;
import com.sanskar.ecommerce02.response.SignupRequest;
import com.sanskar.ecommerce02.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private final AuthService authService;

    public AuthController(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }



    @PostMapping("/sent/login-signup-otp")
    public  ResponseEntity<ApiResponse> sentOtpHandler(@RequestBody LoginOtpRequest req) throws Exception {

        authService.sentLoginOtp(req.getEmail(),req.getRole());
        System.out.println("sucess");
        ApiResponse res = new ApiResponse();
        res.setMessage("otp sent successfully");
        res.setStatus(true);
        return ResponseEntity.ok(res);

    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody SignupRequest req) throws Exception {

        String jwt = authService.createUser(req);
        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setMessage("register success");
        res.setRole(USER_ROLE.ROLE_CUSTOMER);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/signing")
    public  ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest req) throws Exception {

        AuthResponse authResponse = authService.sigin(req);
        System.out.println("sucess");


        return ResponseEntity.ok(authResponse);

    }

}
