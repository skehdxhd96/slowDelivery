package com.example.slowdelivery.domain.cart;

import com.example.slowdelivery.domain.product.Product;
import com.example.slowdelivery.domain.product.ProductOption;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem {

    private Product product;
    private List<ProductOption> options;
    private int quantity; // 수량

    private CartItem(Product product, List<ProductOption> options, int quantity) {
        this.product = product;
        this.options = options;
        this.quantity = quantity;
    }

    public static CartItem of(Product product, List<ProductOption> options, int quantity) {
        return new CartItem(product, options, quantity);
    }
}
