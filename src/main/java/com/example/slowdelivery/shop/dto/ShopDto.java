package com.example.slowdelivery.shop.dto;

import com.example.slowdelivery.shop.domain.openStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class ShopDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Request {

        @NotBlank
        private String phone;

        @NotBlank
        private String shopName;

        @NotNull
        private Integer minimum_price;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Response {


    }
}
