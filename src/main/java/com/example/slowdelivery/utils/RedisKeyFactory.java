package com.example.slowdelivery.utils;

public class RedisKeyFactory {

    public enum Key {
        STOCK, CART, ORDER_ADDRESS, RIDER
    }

    private RedisKeyFactory() { throw new IllegalStateException("RedisKeyFactory의 인스턴스는 생성할 수 없습니다.");}

    private static String generateKey(Long id, Key key) {
        return key.toString() + "-" + id;
    }
    private static String generateKey(String id, Key key) {
        return key + "-" + id;
    }

    public static String generateStockId(Long productId) {
        return generateKey(productId, Key.STOCK);
    }

    public static String generateCartId(Long userId) {
        return generateKey(userId, Key.CART);
    }

    public static String generateOrderAddressKey(String customerAddress) {return generateKey(customerAddress, Key.ORDER_ADDRESS);}
    public static String generateRiderKey(Long riderId) {return generateKey(riderId, Key.RIDER);}
}
