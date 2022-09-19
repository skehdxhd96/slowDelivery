package com.example.slowdelivery.repository.order;

import com.example.slowdelivery.domain.orders.Order;
import com.example.slowdelivery.dto.order.OrderPartition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import static com.example.slowdelivery.utils.RedisKeyFactory.generateCartId;

@Repository
@RequiredArgsConstructor
public class OrderDeliveryWaitingRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    private String generateOrderWaitKey(Long orderId) {
        return generateOrderWaitKey(orderId);
    }

    public void insertOrderWaitingList(Long orderId, Order order) {
        redisTemplate.opsForHash().put(generateOrderWaitKey(orderId), orderId, OrderPartition.of(order));
    }

    public void deleteOrderWaitingList() {

    }
}
