package com.example.slowdelivery.exception;

public enum ErrorCode {
    STOCK_NOT_FOUND(500, "", "재고를 불러올 수 없습니다."),
    STOCK_CANNOT_NEGATIVE(500, "", "재고가 0개 미만입니다."),
    PRODUCT_NOT_FOUND(500, "", "해당 상품을 찾을 수 없습니다."),
    SHOP_NOT_FOUND(500, "", "해당 가게를 찾을 수 없습니다."),
    UNAUTHORIZED_REDIRECT_URI(400, "AU_003", "인증되지 않은 REDIRECT_URI입니다."),
    BAD_LOGIN(400, "AU_004", "잘못된 이메일 또는 패스워드입니다."),
    ALREADY_HAS_SHOP(500, "", "이미 개설된 가게가 존재합니다."),
    SIGNUP_DUPLICATED(400, "", "이미 존재하는 이메일입니다."),
    SHOP_NOT_OPEN(500, "", "영업중인 가게가 아닙니다"),
    MINIMUMPRICE_UNDER(500, "", "현재 주문 가격이 최소배달금액 미만입니다."),
    CANNOT_PUT_DIFFERENT_SHOP_PRODUCT(500, "", "다른 가게의 상품을 담을 수 없습니다."),
    PAY_NOT_FOUND(500, "", "해당 주문의 결제정보를 불러올 수 없습니다."),
    CANNOT_CANCEL_ORDER(500, "", "대기상태의 주문만 취소할 수 있습니다.");

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
