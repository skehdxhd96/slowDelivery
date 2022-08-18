package com.example.slowdelivery.utils;

import org.springframework.stereotype.Component;

public class RedisKeyFactory {

    public enum Key {
        PRODUCTSTOCK
    }

    private RedisKeyFactory() { throw new IllegalStateException("CookieUtils의 인스턴스는 생성할 수 없습니다.");}

    private static String generateKey(Long id, Key key) {
        return key.toString() + "_" + id;
    }

    public static String generateProductStockId(Long id) {
        return generateKey(id, Key.PRODUCTSTOCK);
    }

}
