package com.example.slowdelivery.shop.controller;

import com.example.slowdelivery.common.annotation.CurrentUser;
import com.example.slowdelivery.common.annotation.SellerOnly;
import com.example.slowdelivery.security.common.UserPrincipal;
import com.example.slowdelivery.shop.dto.ShopRequest;
import com.example.slowdelivery.shop.dto.ShopResponse;
import com.example.slowdelivery.shop.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shop")
public class ShopController {

    private final ShopService shopService;

    @PostMapping
    @SellerOnly
    public void createShop(@CurrentUser UserPrincipal user,  @Valid @RequestBody ShopRequest request) {
        shopService.createShop(user, request);
    }

    /**
     * Anonymous - 가게 리스트 목록 api
     */
    @GetMapping
    public ResponseEntity<List<ShopResponse>> getAllShops(@Valid ShopRequest.Search request) {
        List<ShopResponse> shops = shopService.getAllShops(request);
        return ResponseEntity.ok(shops);
    }

    /**
     * Anonymous - 가게 상세 조회 api
     */

}
