package com.sanskar.ecommerce02.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobleException extends Exception{

    @ExceptionHandler(SellerException.class)     // for which class you want to handle the exception
    public ResponseEntity<ErrorDetails> sellerExceptionHandler(SellerException se , WebRequest req){
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setDetails(req.getDescription(false));
        errorDetails.setError(se.getMessage());
        errorDetails.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

//    --------------------------------------------------------------------------------------------------------------

    @ExceptionHandler(ProductException.class)     // for which class you want to handle the exception
    public ResponseEntity<ErrorDetails> ProductExceptionHandler(SellerException se , WebRequest req)
    {

        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setDetails(req.getDescription(false));
        errorDetails.setError(se.getMessage());
        errorDetails.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);

    }

}
