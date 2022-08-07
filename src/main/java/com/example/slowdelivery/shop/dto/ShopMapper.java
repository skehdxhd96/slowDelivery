package com.example.slowdelivery.shop.dto;

import com.example.slowdelivery.shop.domain.Shop;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ShopMapper {

    ShopMapper INSTANCE = Mappers.getMapper(ShopMapper.class);

    ShopDto.Response toDto(Shop shop);

    Shop toEntity(ShopDto.Request request); // openstatus 설정 필요
}
