package com.example.slowdelivery.dto.cart;

import com.example.slowdelivery.domain.cart.CartItemOption;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CartItemOptionRequest {

    @NotNull
    private Long productOptionId;
    @NotBlank
    private String productOptionName;
    @NotNull
    private Integer productOptionPrice;

    @Builder
    public CartItemOptionRequest(Long productOptionId, String productOptionName, Integer productOptionPrice) {
        this.productOptionId = productOptionId;
        this.productOptionName = productOptionName;
        this.productOptionPrice = productOptionPrice;
    }

    public CartItemOption toEntity(Long productOptionId, String productOptionName, Integer productOptionPrice) {
        return new CartItemOption(productOptionId, productOptionName, productOptionPrice);
    }
}
