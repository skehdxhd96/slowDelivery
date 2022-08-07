package com.example.slowdelivery.shop.controller;

import com.example.slowdelivery.common.annotation.CurrentUser;
import com.example.slowdelivery.common.annotation.SellerOnly;
import com.example.slowdelivery.security.common.UserPrincipal;
import com.example.slowdelivery.shop.dto.ShopDto;
import com.example.slowdelivery.shop.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shop")
public class ShopController {

    private final ShopService shopService;

    @PostMapping
    @SellerOnly
    public void createShop(@CurrentUser UserPrincipal user,  @Valid @RequestBody ShopDto.Request request) {

        shopService.createShop(user, request);

    }
}
