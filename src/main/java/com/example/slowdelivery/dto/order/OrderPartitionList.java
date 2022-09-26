package com.example.slowdelivery.dto.order;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderPartitionList {

    private String OrderType;

    private List<OrderPartition> orderList;

    public OrderPartitionList(String orderType, List<OrderPartition> orderList) {
        OrderType = orderType;
        this.orderList = orderList;
    }
}
