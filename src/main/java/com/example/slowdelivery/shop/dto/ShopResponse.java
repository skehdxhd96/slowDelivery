package com.example.slowdelivery.shop.dto;

import com.example.slowdelivery.shop.domain.Shop;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ShopResponse {

    private String shopName;

    private Integer minimumPrice;

    private String status;

    // 배달비 추가해야함

    public static List<ShopResponse> ofList(List<Shop> shop) {
        return shop.stream()
                .map(ShopResponse::of)
                .collect(Collectors.toList());
    }

    public static ShopResponse of(Shop shop) {
        return new ShopResponse(shop.getShopName(), shop.getMinimumPrice(), shop.getOpenStatus().toString());
    }
}
