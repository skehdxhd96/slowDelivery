package com.example.slowdelivery.domain.cart;

import com.example.slowdelivery.domain.product.ProductOption;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItemOption {

    private ProductOption option;

    private CartItemOption(ProductOption option) {
        this.option = option;
    }

    public static CartItemOption of(ProductOption option) {
        return new CartItemOption(option);
    }
}
