package com.example.slowdelivery.repository.product;

import com.example.slowdelivery.domain.product.Product;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.example.slowdelivery.domain.product.QProduct.product;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Product> findByIdWithShop(Long productId) {
        return Optional.ofNullable(queryFactory.select(product)
                .from(product)
                .join(product.shop).fetchJoin()
                .where(product.id.eq(productId))
                .fetchOne());
    }
}
