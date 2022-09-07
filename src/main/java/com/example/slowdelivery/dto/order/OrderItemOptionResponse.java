package com.example.slowdelivery.dto.order;

import com.example.slowdelivery.domain.orders.OrderItemOption;
import lombok.Getter;

@Getter
public class OrderItemOptionResponse {

    private String orderItemOptionName;
    private int orderItemOptionPrice;

    public OrderItemOptionResponse(String orderItemOptionName, int orderItemOptionPrice) {
        this.orderItemOptionName = orderItemOptionName;
        this.orderItemOptionPrice = orderItemOptionPrice;
    }

    public static OrderItemOptionResponse of(OrderItemOption orderItemOption) {
        return new OrderItemOptionResponse(orderItemOption.getOptionName(), orderItemOption.getOptionPrice());
    }
}
