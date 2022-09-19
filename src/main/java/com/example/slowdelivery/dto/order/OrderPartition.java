package com.example.slowdelivery.dto.order;

import com.example.slowdelivery.domain.orders.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderPartition {

    private Long orderId;
    private OrderResponse orderResponse;

    public OrderPartition(Order order) {
        this.orderId = order.getId();
        this.orderResponse = OrderResponse.of(order);
    }

    public static OrderPartition of(Order order) {
        return new OrderPartition(order);
    }
}
