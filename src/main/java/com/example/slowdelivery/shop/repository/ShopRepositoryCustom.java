package com.example.slowdelivery.shop.repository;

import com.example.slowdelivery.shop.domain.Shop;
import com.example.slowdelivery.shop.dto.ShopRequest;

import java.util.List;

public interface ShopRepositoryCustom {

    Boolean existsBySellerId(Long id);

    List<Shop> findAllShop(ShopRequest.Search request);
}
