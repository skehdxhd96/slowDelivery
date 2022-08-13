package com.example.slowdelivery.exception;

public class ProductException extends BusinessException{
    public ProductException(ErrorCode errorCode) {
        super(errorCode);
    }
}
