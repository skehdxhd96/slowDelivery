package com.example.slowdelivery.dto.stock;

import com.example.slowdelivery.domain.stock.Stock;
import com.example.slowdelivery.utils.RedisKeyFactory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StockRequest {

    @NotNull
    private Integer total;

    @NotNull
    private Integer remain;

    @Builder
    public Stock toEntity(Long productId) {
        return Stock.builder()
                .id(RedisKeyFactory.generateProductStockId(productId))
                .productId(productId)
                .total(total)
                .remain(remain)
                .build();
    }
}
