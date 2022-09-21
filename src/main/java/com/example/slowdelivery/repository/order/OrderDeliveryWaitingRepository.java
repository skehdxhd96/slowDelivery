package com.example.slowdelivery.repository.order;

import com.example.slowdelivery.domain.orders.Order;
import com.example.slowdelivery.dto.order.OrderPartition;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.slowdelivery.utils.RedisKeyFactory.generateOrderAddressKey;

@Repository
@RequiredArgsConstructor
public class OrderDeliveryWaitingRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    public void insertOrderWaitingList(String customerAddress, Order order) {

        // Key를 Address로 바꿔야함. orderId로 하면 Rider가 orderList 검색할 때 단건 검색 외에는 불가함.
        redisTemplate.opsForHash().put(generateOrderAddressKey(customerAddress), order.getId(), OrderPartition.of(order));
    }

    public void deleteOrderWaitingList() {

    }

    public List<Object> findOrderWaitingList(String riderAddress, Set<String> keys) {

        return redisTemplate.opsForHash()
                                .multiGet(generateOrderAddressKey(riderAddress), Collections.singleton(keys));
    }

    public Set<String> findOrderWaitingKeys(String riderAddress) {
        Set<String> keyList = new HashSet<>();
        redisTemplate.execute(new RedisCallback<Set<String>>() {
            @Override
            public Set<String> doInRedis(RedisConnection connection) throws DataAccessException {
                ScanOptions options = ScanOptions.scanOptions().match("*").count(50).build();
                Cursor<Map.Entry<byte[], byte[]>> entryCursor = connection.hScan(generateOrderAddressKey(riderAddress).getBytes(), options);

                while(entryCursor.hasNext()) {
                    Map.Entry<byte[], byte[]> next = entryCursor.next();
                    byte[] key = next.getKey();
                    keyList.add(key.toString());
                }

                return keyList;
            }
        });

        return keyList;
    }
}
