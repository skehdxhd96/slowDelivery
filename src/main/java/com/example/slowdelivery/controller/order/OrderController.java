package com.example.slowdelivery.controller.order;

import com.example.slowdelivery.common.annotation.CurrentUser;
import com.example.slowdelivery.common.annotation.CustomerOnly;
import com.example.slowdelivery.common.annotation.SellerOnly;
import com.example.slowdelivery.dto.order.OrderFindRequest;
import com.example.slowdelivery.dto.order.OrderRequest;
import com.example.slowdelivery.dto.order.OrderResponse;
import com.example.slowdelivery.dto.order.OrderUpdateRequest;
import com.example.slowdelivery.security.common.UserPrincipal;
import com.example.slowdelivery.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public ResponseEntity<Void> successOrder(@PathVariable Long orderId, @RequestBody @Valid OrderUpdateRequest request) {
        orderService.successOrder(orderId, request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/api/order/{orderId}/fail")
    public ResponseEntity<Void> failOrder(@PathVariable Long orderId) {
        orderService.failOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/order/history")
    @CustomerOnly
    public ResponseEntity<List<OrderResponse>> getMyOrderHistory(@CurrentUser UserPrincipal user, @RequestBody OrderFindRequest request) {
        List<OrderResponse> orderHistory = orderService.getOrderHistory(user.toCustomer(), request);
        return ResponseEntity.ok(orderHistory);
    }

    @PatchMapping("/api/order/{orderId}/cancel")
    @CustomerOnly
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/order/{shopId}")
    @SellerOnly
    public ResponseEntity<List<OrderResponse>> getOrderList(@PathVariable Long shopId, @RequestBody OrderFindRequest request) {
        List<OrderResponse> orderList = orderService.getOrderList(shopId, request);
        return ResponseEntity.ok(orderList);
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
    public ResponseEntity<OrderResponse> getOrderDetail(@PathVariable Long orderId) {
        OrderResponse orderDetail = orderService.getOrderDetail(orderId);
        return ResponseEntity.ok(orderDetail);
    }
}
