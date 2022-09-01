package com.example.slowdelivery.dto.shop;

import com.example.slowdelivery.domain.shop.Shop;
import com.example.slowdelivery.dto.product.ProductResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ShopFindAllResponse {

    private String shopName;

    private Integer minimumPrice;

    private String status;

    private Integer deliveryTip;

    public static List<ShopFindAllResponse> ofList(List<Shop> shop) {
        return shop.stream()
                .map(ShopFindAllResponse::of)
                .collect(Collectors.toList());
    }

    public static ShopFindAllResponse of(Shop shop) {
        return new ShopFindAllResponse(shop.getShopName(), shop.getMinimumPrice(), shop.getOpenStatus().toString(), shop.getDeliveryTip());
    }
}
