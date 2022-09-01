package com.example.slowdelivery.exception;

public class CartException extends BusinessException {
    public CartException(ErrorCode errorCode) {
        super(errorCode);
    }
}
