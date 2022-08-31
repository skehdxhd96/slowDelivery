package com.example.slowdelivery.dto.cart;

import com.example.slowdelivery.domain.cart.CartItemOption;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CartItemOptionResponse {

    private Long productOptionId;
    private String productOptionName;
    private Integer productOptionPrice;

    @Builder
    public CartItemOptionResponse(Long productOptionId, String productOptionName, Integer productOptionPrice) {
        this.productOptionId = productOptionId;
        this.productOptionName = productOptionName;
        this.productOptionPrice = productOptionPrice;
    }

    public static List<CartItemOptionResponse> ofList(List<CartItemOption> options) {
        return options.stream().map(o -> new CartItemOptionResponse(o.getProductOptionId(),
                                                             o.getProductOptionName(),
                                                             o.getProductOptionPrice())).collect(Collectors.toList());
    }
}
