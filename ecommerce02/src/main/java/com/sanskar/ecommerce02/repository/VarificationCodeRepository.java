package com.sanskar.ecommerce02.repository;

import com.sanskar.ecommerce02.model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VarificationCodeRepository extends JpaRepository<VerificationCode,Long> {
    VerificationCode findByEmail(String mail);
    VerificationCode findByOtp(String otp);
}
