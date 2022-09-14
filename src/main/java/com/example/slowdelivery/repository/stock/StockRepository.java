package com.example.slowdelivery.repository.stock;

import com.example.slowdelivery.domain.cart.Cart;
import com.example.slowdelivery.domain.stock.Stock;
import com.example.slowdelivery.dto.stock.StockRequest;
import com.example.slowdelivery.utils.RedisKeyFactory;
import io.lettuce.core.RedisException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StockRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    private String generateStockKey(Long productId) {
        return RedisKeyFactory.generateStockId(productId);
    }

    // 상품 등록 시 재고 설정
    public void setStock(Long productId, StockRequest request) {
        Stock stock = request.toEntity(productId);
        redisTemplate.opsForHash().put(generateStockKey(productId), productId, stock);
    }

    // 재고 가져오기
    public int getStock(Long productId) {

        //키 만료되었을 경우 RDB에서 찾아오는 로직 필요

        Stock stock = (Stock) redisTemplate.opsForHash()
                .get(generateStockKey(productId), productId);

        return stock.getRemain();
    }

    public Stock decreaseStock(Long productId, int quantity) {
        String key = generateStockKey(productId);

        if(!redisTemplate.hasKey(key)) throw new RedisException("재고 키가 만료되었습니다");
        List<Object> stock = redisTemplate.execute(new SessionCallback<List<Object>>() {
            @Override
            public List<Object> execute(RedisOperations operations) throws DataAccessException {

                try {
                    operations.watch(key);
                    operations.multi();
                    Stock currentStock = (Stock) operations.opsForHash().get(key, productId);
                    currentStock.decreaseStock(quantity);
                    operations.opsForHash().put(key, productId, currentStock);
                    return operations.exec();
                } catch (Exception e) {
                    operations.discard();
                    throw new RedisException("재고 감소 과정에서 오류가 발생하였습니다");
                }
            }
        });

        return (Stock) stock.get(0);
    }

    public Stock increaseStock(Long productId, int quantity) {
        String key = generateStockKey(productId);

        if(!redisTemplate.hasKey(key)) throw new RedisException("재고 키가 만료되었습니다");

        List<Object> stock = redisTemplate.execute(new SessionCallback<List<Object>>() {
            @Override
            public List<Object> execute(RedisOperations operations) throws DataAccessException {
                try {
                    operations.watch(key);
                    operations.multi();
                    Stock currentStock = (Stock) operations.opsForHash().get(key, productId);
                    currentStock.increaseStock(quantity);
                    operations.opsForHash().put(key, productId, currentStock);
                    return operations.exec();
                } catch (Exception e) {
                    operations.discard();
                    throw new RedisException("재고 원복 과정에서 오류가 발생하였습니다.");
                }
            }
        });

        return (Stock) stock.get(0);
    }

    public void RollbackStockOnOrder(Long productId, int quantity) {

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCompletion(int status) {
                if(status == STATUS_ROLLED_BACK) {
                    String key = generateStockKey(productId);
                    Stock stock = (Stock) redisTemplate.opsForHash().get(key, productId);
                    stock.increaseStock(quantity);
                    redisTemplate.opsForHash().put(key, productId, stock);
                }
            }
        });
    }

    public void RollbackStockOnOrderCancel(Long productId, int quantity) {

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCompletion(int status) {
                if(status == STATUS_ROLLED_BACK) {
                    String key = generateStockKey(productId);
                    Stock stock = (Stock) redisTemplate.opsForHash().get(key, productId);
                    stock.decreaseStock(quantity);
                    redisTemplate.opsForHash().put(key, productId, stock);
                }
            }
        });
    }

    // 상품 등록 오류 시 롤백
}
