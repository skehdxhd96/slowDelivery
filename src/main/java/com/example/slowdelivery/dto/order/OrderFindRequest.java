package com.example.slowdelivery.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class OrderFindRequest {

    private String orderStatus;

    private String address;

    private LocalDateTime reserationTime;
}
