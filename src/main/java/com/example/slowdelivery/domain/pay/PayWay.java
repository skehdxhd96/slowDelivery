package com.example.slowdelivery.domain.pay;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PayWay {

    CARD("CARD", "신용카드결제"),
    CASH("CASH", "현금현장결제");

    private final String key;
    private final String value;

    public static PayWay convert(String s) {
        switch (s) {
            case "CARD":
                return PayWay.CARD;
            case "CASH":
                return PayWay.CASH;
            default:
                throw new IllegalStateException("존재하지 않는 결제방식입니다");
        }
    }
}
