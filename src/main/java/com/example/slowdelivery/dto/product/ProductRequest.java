package com.example.slowdelivery.dto.product;

import com.example.slowdelivery.domain.product.Product;
import com.example.slowdelivery.domain.shop.Shop;
import com.example.slowdelivery.dto.stock.StockRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductRequest {

    @NotBlank
    private String productName;

    @NotNull
    private Integer productPrice;

    private List<ProductOptionRequest> options;

    //valid 안붙여도 되나?
    private StockRequest stock;

    public static List<Product> toList(final List<ProductRequest> requests) {
        return requests.stream()
                .map(ProductRequest::toEntity)
                .collect(Collectors.toList());
    }

    public Product toEntity() {
        return Product.builder()
                .productName(productName)
                .productPrice(productPrice)
                .build();
    }
}
