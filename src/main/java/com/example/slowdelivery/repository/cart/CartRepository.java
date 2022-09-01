package com.example.slowdelivery.repository.cart;

import com.example.slowdelivery.domain.cart.Cart;
import com.example.slowdelivery.domain.cart.CartItem;
import com.example.slowdelivery.dto.cart.CartItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

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
    private String generateCartKey(Long userId) {
        return generateCartId(userId);
    }

    public void addProductToCart(Long userId, CartItem item) {
        Cart myCart  = (Cart) redisTemplate.opsForHash().get(generateCartId(userId), userId);
        myCart.addCartItem(item);

        redisTemplate.opsForHash().put(generateCartKey(userId), userId, myCart);
    }

    public Cart getCart(Long userId) {
        return (Cart) redisTemplate.opsForHash().get(generateCartKey(userId), userId);
    }

    public void deleteProduct(Long userId, Long productId) {
        Cart myCart = (Cart) redisTemplate.opsForHash().get(generateCartKey(userId), userId);
        myCart.deleteCartItem(productId);

        redisTemplate.opsForHash().put(generateCartKey(userId), userId, myCart);
    }

    public void hasKeyOrCreateCart(Long userId) {
        // NullPointerException조심
        if(!redisTemplate.hasKey(generateCartId(userId))) {
            redisTemplate.opsForHash().put(generateCartKey(userId), userId, Cart.of(String.valueOf(userId)));
        }
    }
}
