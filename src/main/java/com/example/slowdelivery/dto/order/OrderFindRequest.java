package com.example.slowdelivery.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderFindRequest {

    private String orderStatus;
}
