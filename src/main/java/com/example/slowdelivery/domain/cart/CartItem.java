package com.example.slowdelivery.domain.cart;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem {

    private Long shopId;
    private Long productId;
    private String productName;
    private int productPrice;
    private List<CartItemOption> options;
    private int quantity; // 수량

    @Builder
    public CartItem(Long shopId, Long productId, String productName, int productPrice, List<CartItemOption> options, int quantity) {
        this.shopId = shopId;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.options = options;
        this.quantity = quantity;
    }

    public void chooseOptions(CartItemOption option) {
        this.options.add(option);
    }
}
