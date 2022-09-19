package com.example.slowdelivery.repository.order;

import com.example.slowdelivery.domain.orders.Order;
import com.example.slowdelivery.dto.order.OrderFindRequest;

import java.util.List;

public interface OrderRepositoryCustom {
    List<Order> findByShopIdWithOrderStatus(Long shopId, OrderFindRequest request);
    List<Order> findByCustomerIdWithOrderStatus(Long customerId, OrderFindRequest request);
}