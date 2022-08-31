package com.example.slowdelivery.domain.cart;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.List;

@RedisHash("CART")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {

    @Id
    private String id;

    private List<CartItem> cartItems;

    @Builder
    private Cart(String id) {
        this.id = id;
        this.cartItems = new ArrayList<>();
    }

    public static Cart of(String userId) {
        return new Cart(userId);
    }

    public void addCartItem(CartItem item) {
        this.cartItems.add(item);
    }

    public void deleteCartItem(Long productId) {
        for (CartItem cartItem : this.cartItems) {
            if(cartItem.getProductId() == productId) {
                this.cartItems.remove(cartItem);
                break;
            }
        }
    }
}
