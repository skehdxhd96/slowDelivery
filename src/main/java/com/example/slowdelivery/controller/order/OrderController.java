package com.example.slowdelivery.controller.order;

import com.example.slowdelivery.common.annotation.CurrentUser;
import com.example.slowdelivery.common.annotation.CustomerOnly;
import com.example.slowdelivery.security.common.UserPrincipal;
import com.example.slowdelivery.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/api/order")
    @CustomerOnly
    public ResponseEntity<Void> createOrder(@CurrentUser UserPrincipal user) {
        orderService.createOrder(user.toCustomer());
        return ResponseEntity.noContent().build();
    }
}
