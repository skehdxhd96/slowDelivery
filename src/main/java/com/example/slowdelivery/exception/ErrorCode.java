package com.example.slowdelivery.exception;

public enum ErrorCode {
    UNAUTHORIZED_REDIRECT_URI(400, "AU_003", "인증되지 않은 REDIRECT_URI입니다."),
    BAD_LOGIN(400, "AU_004", "잘못된 이메일 또는 패스워드입니다."),
    ALREADY_HAS_SHOP(500, "", "이미 개설된 가게가 존재합니다."),
    SIGNUP_DUPLICATED(400, "", "이미 존재하는 이메일입니다.");


    private final String code;
    private final String message;
    private final int status;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }
}
