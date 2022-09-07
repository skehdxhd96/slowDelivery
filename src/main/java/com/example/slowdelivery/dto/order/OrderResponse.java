package com.example.slowdelivery.dto.order;

import com.example.slowdelivery.domain.orders.Order;
import com.example.slowdelivery.domain.orders.OrderType;
import com.example.slowdelivery.domain.pay.Pay;
import com.example.slowdelivery.domain.pay.PayStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OrderResponse {

    private String deliveryAddress;
    private int totalPaymentPrice;
    private String msg;
    private String payStatus;
    private String payWay;
    private String orderType;
    private String orderStatus;
    private List<OrderItemResponse> items;

    @Builder
    public OrderResponse(String deliveryAddress, int totalPaymentPrice, String msg,
                         String payStatus, String payWay, String orderType, String orderStatus, List<OrderItemResponse> items) {
        this.deliveryAddress = deliveryAddress;
        this.totalPaymentPrice = totalPaymentPrice;
        this.msg = msg;
        this.payStatus = payStatus;
        this.payWay = payWay;
        this.orderType = orderType;
        this.orderStatus = orderStatus;
        this.items = items;
    }

    public static OrderResponse of(Order order, Pay pay) {
        return OrderResponse.builder()
                .deliveryAddress(order.getDeliveryAddress())
                .totalPaymentPrice(pay.getTotalPaymentPrice())
                .msg(order.getMsg())
                .payStatus(pay.getPaystatus().toString())
                .payWay(pay.getPayway().toString())
                .orderType(order.getOrderType().toString())
                .orderStatus(order.getOrderStatus().toString())
                .items(order.getItems().stream()
                                        .map(i -> OrderItemResponse.of(i))
                                        .collect(Collectors.toList()))
                .build();
    }
}
