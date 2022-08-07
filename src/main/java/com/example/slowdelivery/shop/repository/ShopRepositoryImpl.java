package com.example.slowdelivery.shop.repository;

import static com.example.slowdelivery.shop.domain.QShop.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ShopRepositoryImpl implements ShopRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Boolean existsBySellerId(Long id) {
        Integer fetchOne = queryFactory.selectOne()
                .from(shop)
                .join(shop.seller) // 패치는 왜 안됨
                .where(shop.seller.id.eq(id))
                .fetchFirst();

        return fetchOne != null;
    }
}
