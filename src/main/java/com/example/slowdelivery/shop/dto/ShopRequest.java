package com.example.slowdelivery.shop.dto;

import com.example.slowdelivery.Seller.domain.Seller;
import com.example.slowdelivery.shop.domain.Shop;
import com.example.slowdelivery.shop.domain.openStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.core.annotation.Order;

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

    public Shop toShop(Seller seller) {
        return Shop.builder()
                .seller(seller)
                .shopName(shopName)
                .minimumPrice(minimumPrice)
                .phone(phone)
                .openStatus(openStatus.CLOSE)
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
