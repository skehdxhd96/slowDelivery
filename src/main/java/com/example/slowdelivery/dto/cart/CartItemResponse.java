package com.example.slowdelivery.dto.cart;

import com.example.slowdelivery.domain.cart.CartItem;
import com.example.slowdelivery.domain.product.ProductOption;
import com.example.slowdelivery.dto.product.ProductOptionResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CartItemResponse {

    private Long productId;
    private String productName;
    private Integer productPrice;
    private Integer quantity;
    private List<CartItemOptionResponse> options = new ArrayList<>();

    @Builder
    public CartItemResponse(Long productId, String productName, Integer productPrice, Integer quantity, List<CartItemOptionResponse> options) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.options = options;
    }

    public static List<CartItemResponse> ofList(final List<CartItem> items) {
        return items.stream()
                .map(o -> new CartItemResponse(o.getProductId(),
                                               o.getProductName(),
                                               o.getProductPrice(),
                                               o.getQuantity(),
                                               CartItemOptionResponse.ofList(o.getOptions())))
                .collect(Collectors.toList());
    }
}
