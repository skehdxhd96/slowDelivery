package com.example.slowdelivery.dto.shop;

import com.example.slowdelivery.domain.shop.Shop;
import com.example.slowdelivery.dto.product.ProductResponse;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ShopResponse {

    private String shopName;

    private Integer minimumPrice;

    private String status;

    private List<ProductResponse> shopProducts;

    // 배달비 추가해야함

    @Builder
    public ShopResponse(String shopName, Integer minimumPrice, String status, List<ProductResponse> shopProducts) {
        this.shopName = shopName;
        this.minimumPrice = minimumPrice;
        this.status = status;
        this.shopProducts = shopProducts;
    }

    public static ShopResponse of(Shop shop) {

        return ShopResponse.builder()
                .shopName(shop.getShopName())
                .minimumPrice(shop.getMinimumPrice())
                .status(shop.getOpenStatus().toString())
                .shopProducts(ProductResponse.ofList(shop.getProducts()))
                .build();
    }
}
