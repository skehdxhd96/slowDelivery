package com.example.slowdelivery.service.shop;

import com.example.slowdelivery.domain.seller.Seller;
import com.example.slowdelivery.dto.product.ProductResponse;
import com.example.slowdelivery.dto.shop.ShopResponse;
import com.example.slowdelivery.exception.ErrorCode;
import com.example.slowdelivery.exception.ShopException;
import com.example.slowdelivery.security.common.UserPrincipal;
import com.example.slowdelivery.domain.shop.Shop;
import com.example.slowdelivery.dto.shop.ShopRequest;
import com.example.slowdelivery.dto.shop.ShopFindAllResponse;
import com.example.slowdelivery.repository.shop.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
    public List<ShopFindAllResponse> getAllShops(ShopRequest.Search request) {
        List<Shop> shops = shopRepository.findAllShop(request);
        return ShopFindAllResponse.ofList(shops);
    }

    @Transactional(readOnly = true)
    public ShopResponse getShopDetail(Long id) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new ShopException(ErrorCode.SHOP_NOT_FOUND));

        return ShopResponse.of(shop);
    }

    private void validateShop(UserPrincipal user) {
        Boolean alreadyHasShop = shopRepository.existsBySellerId(user.getUser().getId());

        if(alreadyHasShop)
            throw new ShopException(ErrorCode.ALREADY_HAS_SHOP);
    }
}
