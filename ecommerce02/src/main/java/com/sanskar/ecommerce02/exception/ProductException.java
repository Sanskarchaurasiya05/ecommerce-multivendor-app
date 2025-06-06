package com.sanskar.ecommerce02.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;


public class ProductException extends Exception {

    public ProductException(String message){
        super(message);
    }
}
