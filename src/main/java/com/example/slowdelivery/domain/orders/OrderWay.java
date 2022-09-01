package com.example.slowdelivery.domain.orders;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderWay {

    CARD("CARD", "신용카드결제"),
    CASH("CASH", "현금현장결제");

    private final String key;
    private final String value;
}
