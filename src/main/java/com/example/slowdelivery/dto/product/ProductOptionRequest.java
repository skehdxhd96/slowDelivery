package com.example.slowdelivery.dto.product;

import com.example.slowdelivery.domain.product.Product;
import com.example.slowdelivery.domain.product.ProductOption;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductOptionRequest {

    @NotBlank
    private String productOptionName;

    @NotNull
    private Integer productOptionPrice;

    public static List<ProductOption> toList(final List<ProductOptionRequest> requests) {
        return requests.stream()
                .map(ProductOptionRequest::toEntity)
                .collect(Collectors.toList());
    }

    public ProductOption toEntity() {

        return new ProductOption(productOptionName, productOptionPrice);
    }
}
