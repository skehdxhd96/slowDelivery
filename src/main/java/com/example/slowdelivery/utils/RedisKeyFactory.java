package com.example.slowdelivery.utils;

import org.springframework.stereotype.Component;

public class RedisKeyFactory {

    public enum Key {
        STOCK, CART
    }

    private RedisKeyFactory() { throw new IllegalStateException("RedisKeyFactory의 인스턴스는 생성할 수 없습니다.");}

    private static String generateKey(Long id, Key key) {
        return key.toString() + "-" + id;
    }

    public static String generateStockId(Long productId) {
        return generateKey(productId, Key.STOCK);
    }

    public static String generateCartId(Long userId) {
        return generateKey(userId, Key.CART);
    }

}
