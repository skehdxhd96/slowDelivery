package com.example.slowdelivery.controller.order;

import com.example.slowdelivery.common.annotation.CurrentUser;
import com.example.slowdelivery.common.annotation.CustomerOnly;
import com.example.slowdelivery.dto.order.OrderRequest;
import com.example.slowdelivery.security.common.UserPrincipal;
import com.example.slowdelivery.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/api/order")
    @CustomerOnly
    public ResponseEntity<Void> createOrder(@CurrentUser UserPrincipal user, @RequestBody @Valid OrderRequest request) {
        orderService.createOrder(user.toCustomer(), request);
        return ResponseEntity.noContent().build();
    }
}
