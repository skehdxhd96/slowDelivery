package com.example.slowdelivery.repository.shop;

import com.example.slowdelivery.domain.shop.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> , ShopRepositoryCustom{

}
