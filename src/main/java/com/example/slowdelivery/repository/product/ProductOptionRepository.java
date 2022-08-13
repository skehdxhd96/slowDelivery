package com.example.slowdelivery.repository.product;

import com.example.slowdelivery.domain.product.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

    @Modifying
    @Query("delete from ProductOption o where o.product.id = :id")
    void deleteByProductId(@Param("id") Long id);

}
