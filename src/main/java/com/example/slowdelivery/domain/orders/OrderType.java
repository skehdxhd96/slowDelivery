package com.example.slowdelivery.domain.orders;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderType {

    GENERAL_DELIVERY("GENERAL_DELIVERY", "일반배달"),
    SLOW_DELIVERY("SLOW_DELIVERY", "느린배달");

    private final String key;
    private final String value;
    public static OrderType convert(String s) {
        switch(s) {
            case "GENERAL":
                return OrderType.GENERAL_DELIVERY;
            case "SLOW":
                return OrderType.SLOW_DELIVERY;
            default:
                throw new IllegalStateException("주문 방식을 선택해주세요");
        }
    }
}
