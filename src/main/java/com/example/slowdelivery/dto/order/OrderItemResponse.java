package com.example.slowdelivery.dto.order;

import com.example.slowdelivery.domain.orders.OrderItem;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OrderItemResponse {

    private String orderItemName;
    private int orderItemPrice;
    private int orderItemQuantity;
    private List<OrderItemOptionResponse> options;

    @Builder
    public OrderItemResponse(String orderItemName, int orderItemPrice, int orderItemQuantity, List<OrderItemOptionResponse> options) {
        this.orderItemName = orderItemName;
        this.orderItemPrice = orderItemPrice;
        this.orderItemQuantity = orderItemQuantity;
        this.options = options;
    }

    public static OrderItemResponse of(OrderItem orderItem) {
        return OrderItemResponse.builder()
                .orderItemName(orderItem.getProductName())
                .orderItemPrice(orderItem.getProductPrice())
                .orderItemQuantity(orderItem.getQuantity())
                .options(orderItem.getOrderItemOptions().stream()
                                                        .map(o -> OrderItemOptionResponse.of(o))
                                                        .collect(Collectors.toList()))
                .build();
    }
}
