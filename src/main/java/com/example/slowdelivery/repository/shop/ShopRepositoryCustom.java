package com.example.slowdelivery.repository.shop;

import com.example.slowdelivery.domain.shop.Shop;
import com.example.slowdelivery.dto.shop.ShopRequest;

import java.util.List;

public interface ShopRepositoryCustom {

    Boolean existsBySellerId(Long id);

    List<Shop> findAllShop(ShopRequest.Search request);
}
