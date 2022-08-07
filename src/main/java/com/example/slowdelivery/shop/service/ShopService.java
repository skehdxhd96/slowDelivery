package com.example.slowdelivery.shop.service;

import com.example.slowdelivery.exception.ErrorCode;
import com.example.slowdelivery.exception.ShopException;
import com.example.slowdelivery.security.common.UserPrincipal;
import com.example.slowdelivery.shop.domain.Shop;
import com.example.slowdelivery.shop.dto.ShopDto;
import com.example.slowdelivery.shop.dto.ShopMapper;
import com.example.slowdelivery.shop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final ShopMapper mapper;

    public Long createShop(UserPrincipal user, ShopDto.Request request) {


        Boolean alreadyHasShop = shopRepository.existsBySellerId(user.getUser().getId());

        if(alreadyHasShop)
            throw new ShopException(ErrorCode.ALREADY_HAS_SHOP);

        Shop shopEntity = mapper.toEntity(request);

        Shop shop = shopRepository.save(shopEntity); // userId 안들어감 -> MapStruct 추정

        return shop.getId();
    }
}
