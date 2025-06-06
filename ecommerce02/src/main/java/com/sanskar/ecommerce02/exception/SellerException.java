package com.sanskar.ecommerce02.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;


public class SellerException extends Exception{

    public SellerException(String message){
        super(message);
    }
}
