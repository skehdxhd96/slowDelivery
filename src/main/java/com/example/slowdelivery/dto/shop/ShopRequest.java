package com.example.slowdelivery.dto.shop;

import com.example.slowdelivery.domain.seller.Seller;
import com.example.slowdelivery.domain.shop.Shop;
import com.example.slowdelivery.domain.shop.openStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ShopRequest {

    @NotBlank
    private String phone;

    @NotBlank
    private String shopName;

    @NotNull
    private Integer minimumPrice;

    @NotNull
    private Integer deliveryTip;

    @NotNull
    @Max(value = 5, message = "최대 5명까지만 가능합니다")
    @Min(value = 2, message = "최소 2명부터 가능합니다")
    private Integer deliveryPeople;

    public Shop toShop(Seller seller) {
        return Shop.builder()
                .seller(seller)
                .shopName(shopName)
                .minimumPrice(minimumPrice)
                .phone(phone)
                .deliveryTip(deliveryTip)
                .openStatus(openStatus.CLOSE)
                .deliveryPeople(deliveryPeople)
                .build();
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Search {

        //검색기준
        private String shopName;

        //정렬기준
        private OrderDto ordered;

        // 배달비 추가해야함
    }
}
