package com.example.slowdelivery.repository.order;

import com.example.slowdelivery.domain.cart.Cart;
import com.example.slowdelivery.domain.orders.Order;
import com.example.slowdelivery.domain.orders.OrderType;
import com.example.slowdelivery.domain.rider.Rider;
import com.example.slowdelivery.domain.stock.Stock;
import com.example.slowdelivery.dto.order.OrderPartition;
import com.example.slowdelivery.dto.order.OrderPartitionList;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.slowdelivery.utils.RedisKeyFactory.generateOrderAddressKey;
import static com.example.slowdelivery.utils.RedisKeyFactory.generateRiderKey;

@Repository
@RequiredArgsConstructor
public class OrderDeliveryWaitingRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String SLOW_DELIVERY = "SLOW_DELIVERY";

    public void insertOrderWaitingList(String customerAddress, Order order) {

        // Key를 Address로 바꿔야함. orderId로 하면 Rider가 orderList 검색할 때 단건 검색 외에는 불가함.
        // 아얘 여기서부터 넣을 때 느린배달은 묶어서 넣어주도록 고쳐야함
        // key를 찾을 때 느린배달을 확인하기에는 몇개인지도 모를 order를 일일히 뒤져야하며
        // get으로 찾아서 application level에서 반복문을 통해 다시 stream.map해서 새로운 response로 반환해도 될거같긴 하다..?
        // 일단 insert 시점 or get 시점 둘 중 하나로 잡고 진행
        redisTemplate.opsForHash().put(generateOrderAddressKey(customerAddress), order.getId(), OrderPartition.of(order));
    }

    public void insertSlowOrderWaitingList(String customerAddress, List<Order> orderList) {

        String key = SLOW_DELIVERY + orderList.get(0).getId();

        List<OrderPartition> slowOrderList = orderList.stream().map(o -> OrderPartition.of(o)).collect(Collectors.toList());
        OrderPartitionList slowOrder = new OrderPartitionList(SLOW_DELIVERY, slowOrderList);

        redisTemplate.opsForHash().put(generateOrderAddressKey(customerAddress), key, slowOrder);
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

    public void standByOrderStart(Order order, Rider rider) {
        // 주문 가능 라이더 목록에서 삭제 + 주문 요청 목록에서 삭제

        String hashkey;
        if(order.getOrderType() == OrderType.SLOW_DELIVERY) hashkey = SLOW_DELIVERY + order.getId();
        else hashkey = String.valueOf(order.getId());

        redisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {

                operations.watch(generateRiderKey(rider.getId()));
                operations.watch(generateOrderAddressKey(rider.getAddress()));

                operations.multi();

                operations.opsForHash().delete(generateRiderKey(rider.getId()), rider.getId());
                operations.opsForHash().delete(generateOrderAddressKey(rider.getAddress()), hashkey);

                return operations.exec();
            }
        });
    }

    public void RollbackDeliveryRequest(Order order, Rider rider, List<Order> orderList) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCompletion(int status) {
                if(status == STATUS_ROLLED_BACK) {
                    redisTemplate.opsForHash().put(generateRiderKey(rider.getId()), rider.getId(), rider);

                    if(order.getOrderType() == OrderType.SLOW_DELIVERY) {
                        insertSlowOrderWaitingList(rider.getAddress(), orderList);
                    } else {
                        insertOrderWaitingList(rider.getAddress(), order);
                    }
                }
            }
        });
    }
}
