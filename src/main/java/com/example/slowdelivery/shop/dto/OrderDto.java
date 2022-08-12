package com.example.slowdelivery.shop.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderDto {

    MINIMUMPRICE("minimumPrice", "최저주문가격순");

    private final String key;
    private final String value;
}

