package com.example.slowdelivery.dto.product;

import com.example.slowdelivery.domain.product.ProductOption;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductOptionResponse {

    private String productOptionName;
    private Integer productOptionPrice;

    public static List<ProductOptionResponse> ofList(final List<ProductOption> options) {
        return options.stream()
                .map(o -> new ProductOptionResponse(o.getProductOptionName(), o.getProdcutOptionPrice()))
                .collect(Collectors.toList());
    }
}
