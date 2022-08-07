package com.example.slowdelivery.exception;

public class ShopException extends BusinessException{
    public ShopException(ErrorCode errorCode) {
        super(errorCode);
    }
}
