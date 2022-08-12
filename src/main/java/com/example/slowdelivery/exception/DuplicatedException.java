package com.example.slowdelivery.exception;

public class DuplicatedException extends BusinessException{
    public DuplicatedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
