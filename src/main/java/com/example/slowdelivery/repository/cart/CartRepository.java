package com.example.slowdelivery.repository.cart;

import com.example.slowdelivery.domain.cart.Cart;
import com.example.slowdelivery.utils.RedisKeyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import static com.example.slowdelivery.utils.RedisKeyFactory.*;

@Repository
@RequiredArgsConstructor
public class CartRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * TODO:
     * Cart생성하기
     * 아무것도 없을 떄 Cart에 상품 담기
     * 장바구니에 무언가 있을 떄 상품 담기
     * Cart에 담겨진 상품 삭제
     * Cart전체 비우기
     * Cart에 담겨진 상품 전체 조회 => null이면 장바구니가 없는거임
     */

    public void addProductToCart(Long userId) {
        String cartId = generateCartId(userId);

    }

    public Cart getCart() {

    }

    public boolean validate(Long userId) {
        return redisTemplate.hasKey(generateCartId(userId));
    }

    public String getCartKey(Long userId) {
        final String cartKey = generateCartId(userId);
        return redisTemplate.getStringSerializer().serialize(cartKey);
    }
}
