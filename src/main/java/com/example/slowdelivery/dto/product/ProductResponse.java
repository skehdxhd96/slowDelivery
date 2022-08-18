package com.example.slowdelivery.dto.product;

import com.example.slowdelivery.domain.product.Product;
import com.example.slowdelivery.domain.product.ProductOption;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductResponse {

    private String productName;
    private Integer productPrice;
    private Integer stock;
    private List<ProductOptionResponse> productOptions;

    //재고
    //보여줄수 없는 상태??

    @Builder
    public ProductResponse(String productName, Integer productPrice, List<ProductOptionResponse> productOptions) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productOptions = productOptions;
    }

    public static ProductResponse of(final Product product) {
        return ProductResponse.builder()
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .productOptions(ProductOptionResponse.ofList(product.getOptions()))
                .build();
    }

    public static List<ProductResponse> ofList(final List<Product> products) {
        return products.stream()
                .map(p -> new ProductResponse(p.getProductName(),
                        p.getProductPrice(),
                        null))
                .collect(Collectors.toList());
    }
}
