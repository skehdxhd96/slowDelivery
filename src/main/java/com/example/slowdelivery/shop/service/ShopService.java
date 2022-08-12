package com.example.slowdelivery.shop.service;

import com.example.slowdelivery.Seller.domain.Seller;
import com.example.slowdelivery.exception.ErrorCode;
import com.example.slowdelivery.exception.ShopException;
import com.example.slowdelivery.security.common.UserPrincipal;
import com.example.slowdelivery.shop.domain.Shop;
import com.example.slowdelivery.shop.dto.ShopRequest;
import com.example.slowdelivery.shop.dto.ShopResponse;
import com.example.slowdelivery.shop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;

    @Transactional
    public Long createShop(UserPrincipal user, ShopRequest request) {
        validateShop(user);
        Shop shop = shopRepository.save(request.toShop((Seller) user.getUser()));
        return shop.getId();
    }

    @Transactional(readOnly = true)
    public List<ShopResponse> getAllShops(ShopRequest.Search request) {
        List<Shop> shops = shopRepository.findAllShop(request);
        return ShopResponse.ofList(shops);
    }

    private void validateShop(UserPrincipal user) {
        Boolean alreadyHasShop = shopRepository.existsBySellerId(user.getUser().getId());

        if(alreadyHasShop)
            throw new ShopException(ErrorCode.ALREADY_HAS_SHOP);
    }
}
