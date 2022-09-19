package com.example.slowdelivery.dto.order;

import com.example.slowdelivery.domain.orders.Order;
import com.example.slowdelivery.domain.orders.OrderType;
import com.example.slowdelivery.domain.pay.Pay;
import com.example.slowdelivery.domain.pay.PayStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
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
    private String shopName;
    private int deliveryTip;
    private int totalOrderPrice;
    private LocalDateTime reservationTime;
    private LocalDateTime orderCompleteTime;
    private List<OrderItemResponse> items;

    @Builder
    public OrderResponse(String deliveryAddress, int totalPaymentPrice, String msg, String payStatus, String payWay, String orderType, String orderStatus, String shopName,
                         int deliveryTip, int totalOrderPrice, LocalDateTime reservationTime, LocalDateTime orderCompleteTime, List<OrderItemResponse> items) {
        this.deliveryAddress = deliveryAddress;
        this.totalPaymentPrice = totalPaymentPrice;
        this.msg = msg;
        this.payStatus = payStatus;
        this.payWay = payWay;
        this.orderType = orderType;
        this.orderStatus = orderStatus;
        this.shopName = shopName;
        this.deliveryTip = deliveryTip;
        this.totalOrderPrice = totalOrderPrice;
        this.reservationTime = reservationTime;
        this.orderCompleteTime = orderCompleteTime;
        this.items = items;
    }

    public static OrderResponse of(Order order) {
        return OrderResponse.builder()
                .deliveryAddress(order.getDeliveryAddress())
                .totalPaymentPrice(order.getTotalOrderPrice() + order.getDeliveryTip())
                .orderType(order.getOrderType().toString())
                .items(order.getItems().stream()
                        .map(i -> OrderItemResponse.of(i))
                        .collect(Collectors.toList()))
                .shopName(order.getShopName())
                .deliveryTip(order.getDeliveryTip())
                .totalOrderPrice(order.getTotalOrderPrice())
                .reservationTime(order.getReservationTime())
                .build();
    }

    public static OrderResponse Deatilof(Order order, Pay pay) {
        return OrderResponse.builder()
                .deliveryAddress(order.getDeliveryAddress())
                .totalPaymentPrice(pay.getTotalPaymentPrice())
                .msg(order.getMsg())
                .payStatus(pay.getPaystatus().toString())
                .payWay(pay.getPayway().toString())
                .orderType(order.getOrderType().toString())
                .orderStatus(order.getOrderStatus().toString())
                .shopName(order.getShopName())
                .deliveryTip(order.getDeliveryTip())
                .totalOrderPrice(order.getTotalOrderPrice())
                .reservationTime(order.getReservationTime())
                .orderCompleteTime(order.getOrderCompleteTime())
                .items(order.getItems().stream()
                                        .map(i -> OrderItemResponse.of(i))
                                        .collect(Collectors.toList()))
                .build();
    }
}
