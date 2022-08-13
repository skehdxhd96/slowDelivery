package com.example.slowdelivery.repository.product;

import com.example.slowdelivery.domain.product.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
}
