package com.example.slowdelivery.repository.shop;

import com.example.slowdelivery.domain.shop.Shop;
import com.example.slowdelivery.dto.shop.OrderDto;
import com.example.slowdelivery.dto.shop.ShopRequest;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.slowdelivery.domain.shop.QShop.shop;

@RequiredArgsConstructor
public class ShopRepositoryImpl implements ShopRepositoryCustom{

    /**
     * TODO : 배달 비 낮은 순
     */
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

    // Index 컬럼 정해야함
    @Override
    public List<Shop> findAllShop(ShopRequest.Search request) {
        return queryFactory.select(shop)
                .from(shop)
                .where(shopNameContains(request.getShopName()))
                .orderBy(ordered(request.getOrdered()))
                .fetch();
    }

    private BooleanExpression shopNameContains(String shopName) {
        if(shopName == null) {
            return null;
        }
        return shop.shopName.contains(shopName);
    }

    private OrderSpecifier ordered(OrderDto ordered) {
        if(ordered == OrderDto.MINIMUMPRICE) {
            return shop.minimumPrice.asc();
        }

        /**
         * Default : 주문 많은 순 or 거리 순 / 문 열린 가게 안열린 가게
         */
        return shop.createdDate.desc();
    }
}
