package com.example.slowdelivery.repository.stock;

import com.example.slowdelivery.domain.stock.Stock;
import com.example.slowdelivery.dto.stock.StockRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StockRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    // 상품 등록 시 재고 설정
    public void setStock(Long productId, StockRequest request) {
        Stock stock = request.toEntity(productId);
        redisTemplate.opsForHash().put("STOCK", stock.getId() , stock);
    }

    // 상품 등록 오류 시 롤백
}
