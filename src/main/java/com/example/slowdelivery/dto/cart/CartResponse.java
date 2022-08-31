package com.example.slowdelivery.dto.cart;

import com.example.slowdelivery.domain.cart.Cart;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CartResponse {

    private String cartId;
    private List<CartItemResponse> items = new ArrayList<>();

    @Builder
    public CartResponse(String cartId, List<CartItemResponse> items) {
        this.cartId = cartId;
        this.items = items;
    }

    public static CartResponse of(Cart cart) {
        return CartResponse.builder()
                .cartId(cart.getId())
                .items(CartItemResponse.ofList(cart.getCartItems()))
                .build();
    }
}
