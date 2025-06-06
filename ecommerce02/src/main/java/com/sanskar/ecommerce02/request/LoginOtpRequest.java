package com.sanskar.ecommerce02.request;

import com.sanskar.ecommerce02.domain.USER_ROLE;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginOtpRequest {
   private  String email;
    private String otp;
   private USER_ROLE role;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public USER_ROLE getRole() {
        return role;
    }

    public void setRole(USER_ROLE role) {
        this.role = role;
    }
}
