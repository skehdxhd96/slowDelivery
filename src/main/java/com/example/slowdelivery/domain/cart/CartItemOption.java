package com.example.slowdelivery.domain.cart;

import com.example.slowdelivery.domain.product.ProductOption;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItemOption {

    private Long productOptionId;
    private String productOptionName;
    private int productOptionPrice;

    @Builder
    public CartItemOption(Long productOptionId, String productOptionName, int productOptionPrice) {
        this.productOptionId = productOptionId;
        this.productOptionName = productOptionName;
        this.productOptionPrice = productOptionPrice;
    }
}
