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
}
