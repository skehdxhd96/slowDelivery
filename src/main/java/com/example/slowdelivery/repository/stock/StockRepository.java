package com.example.slowdelivery.repository.stock;

import com.example.slowdelivery.domain.stock.Stock;
import com.example.slowdelivery.dto.stock.StockRequest;
import com.example.slowdelivery.utils.RedisKeyFactory;
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
        redisTemplate.opsForHash().put(RedisKeyFactory.generateStockId(productId), productId, stock);
    }

    // 재고 가져오기
    public int getStock(Long productId) {
        Stock stock = (Stock) redisTemplate.opsForHash()
                .get(RedisKeyFactory.generateStockId(productId), productId);

        return stock.getRemain();
    }

    // 상품 등록 오류 시 롤백
}
