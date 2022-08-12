package com.example.slowdelivery.shop.repository;

import com.example.slowdelivery.shop.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> , ShopRepositoryCustom{

}
