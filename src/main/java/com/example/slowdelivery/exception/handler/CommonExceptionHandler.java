package com.example.slowdelivery.exception.handler;

import com.example.slowdelivery.common.dto.ErrorResponse;
import com.example.slowdelivery.exception.DuplicatedException;
import com.example.slowdelivery.exception.ShopException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(ShopException.class)
    public ResponseEntity<ErrorResponse> handlerShopException(ShopException e) {
        ErrorResponse response = ErrorResponse.of(e.getErrorCode());

        return new ResponseEntity<>(response, HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }

    @ExceptionHandler(DuplicatedException.class)
    public ResponseEntity<ErrorResponse> handlerDuplicatedException(DuplicatedException e) {
        ErrorResponse response = ErrorResponse.of(e.getErrorCode());

        return new ResponseEntity<>(response, HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }
}
