package com.sanskar.ecommerce02.util;

import java.util.Random;

public class OtpUtil {
//    logic for generating new otp

    public static String generateOtp(){
        int otpLength = 6;

        Random random = new Random();

        StringBuilder otp = new StringBuilder(otpLength);

        for(int i=0;i<otpLength;i++){
            otp.append(random.nextInt(10));
        }
   return otp.toString();
    }
}
