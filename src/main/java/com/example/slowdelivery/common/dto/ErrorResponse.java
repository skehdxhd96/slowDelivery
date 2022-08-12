package com.example.slowdelivery.common.dto;

import com.example.slowdelivery.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private String msg;
    private String code;

    private ErrorResponse(ErrorCode errorCode) {
        this.msg = errorCode.getMessage();
        this.code = errorCode.getCode();
    }

    public static ErrorResponse of(ErrorCode code) {
        return new ErrorResponse(code);
    }
}
