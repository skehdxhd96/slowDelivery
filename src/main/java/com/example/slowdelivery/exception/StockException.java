package com.example.slowdelivery.exception;

public class StockException extends BusinessException{
    public StockException(ErrorCode errorCode) {
        super(errorCode);
    }
}
