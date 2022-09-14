package com.example.slowdelivery.controller.order;

import com.example.slowdelivery.common.annotation.CurrentUser;
import com.example.slowdelivery.common.annotation.CustomerOnly;
import com.example.slowdelivery.common.annotation.SellerOnly;
import com.example.slowdelivery.dto.order.OrderRequest;
import com.example.slowdelivery.dto.order.OrderResponse;
import com.example.slowdelivery.security.common.UserPrincipal;
import com.example.slowdelivery.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/api/order")
    @CustomerOnly
    public ResponseEntity<OrderResponse> createOrder(@CurrentUser UserPrincipal user, @RequestBody @Valid OrderRequest request) {
        OrderResponse order = orderService.createOrder(user.toCustomer(), request);
        return ResponseEntity.ok(order);
    }

    @PatchMapping("/api/order/{orderId}/success")
    public ResponseEntity<Void> successOrder(@PathVariable Long orderId) {
        orderService.successOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/api/order/{orderId}/fail")
    public ResponseEntity<Void> failOrder(@PathVariable Long orderId) {
        orderService.failOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 소비자 - 자신의 주문내역 확인
     */
    @GetMapping("/api/order/history")
    @CustomerOnly
    public void getMyOrderHistory() {

    }

    @PatchMapping("/api/order/{orderId}/cancel")
    @CustomerOnly
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/order/{shopId}")
    @SellerOnly
    public void getOrderList(@PathVariable Long shopId) {

    }

    @PatchMapping("/api/order/{orderId}/complete")
    @SellerOnly
    public ResponseEntity<Void> completeOrderState(@PathVariable Long orderId) {
        orderService.DoneOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/api/order/{orderId}/reject")
    @SellerOnly
    public ResponseEntity<Void> rejectOrder(@PathVariable Long orderId) {
        orderService.rejectOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/order/{orderId}")
    public void getOrderDetail(@PathVariable Long orderId) {

    }
}
