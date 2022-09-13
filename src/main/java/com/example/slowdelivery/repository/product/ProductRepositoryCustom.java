package com.example.slowdelivery.repository.product;

import com.example.slowdelivery.domain.product.Product;

import java.util.Optional;

public interface ProductRepositoryCustom {

    public Optional<Product> findByIdWithShop(Long productId);
}
