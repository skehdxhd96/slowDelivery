package com.example.slowdelivery.dto.cart;

import com.example.slowdelivery.domain.cart.CartItem;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CartItemRequest {

    @NotNull
    private Long shopId;
    @NotNull
    private Long productId;
    @NotBlank
    private String productName;
    @NotNull
    private Integer productPrice;
    @NotNull
    private Integer quantity;
    private List<CartItemOptionRequest> options = new ArrayList<>();

    @Builder
    public CartItemRequest(Long shopId, Long productId, String productName,
                           Integer productPrice, Integer quantity, List<CartItemOptionRequest> options) {
        this.shopId = shopId;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.options = options;
    }

    public CartItem toEntity() {
        return CartItem.builder()
                .shopId(shopId)
                .productId(productId)
                .productName(productName)
                .productPrice(productPrice)
                .quantity(quantity)
                .options(options.stream()
                                    .map(o -> o.toEntity(o.getProductOptionId(),
                                                        o.getProductOptionName(),
                                                        o.getProductOptionPrice()))
                                    .collect(Collectors.toList()))
                .build();
    }
}
