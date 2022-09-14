package com.example.slowdelivery.exception;

public class PayException extends BusinessException{
    public PayException(ErrorCode errorCode) {
        super(errorCode);
    }
}
