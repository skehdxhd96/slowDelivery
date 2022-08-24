package com.example.slowdelivery.dto.cart;

import com.example.slowdelivery.domain.cart.Cart;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CartRequest {

    private List<CartItemRequest> items = new ArrayList<>();

    private CartRequest(List<CartItemRequest> items) {
        this.items = items;
    }

    public Cart toEntity(String cartId) {
        Cart cart = Cart.of(cartId);
    }
}
