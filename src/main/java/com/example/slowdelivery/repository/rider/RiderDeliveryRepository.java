package com.example.slowdelivery.repository.rider;

import com.example.slowdelivery.domain.rider.Rider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import static com.example.slowdelivery.utils.RedisKeyFactory.generateRiderKey;

@Repository
@RequiredArgsConstructor
public class RiderDeliveryRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    public void deliveryOn(Rider rider) {
        // 배달 가능 대기 리스트에 자신을 추가
        redisTemplate.opsForHash().put(generateRiderKey(rider.getId()), rider.getId(), rider);
    }

    public void deliveryOff(Rider rider) {
        redisTemplate.opsForHash().delete(generateRiderKey(rider.getId()), rider.getId());
    }
}
