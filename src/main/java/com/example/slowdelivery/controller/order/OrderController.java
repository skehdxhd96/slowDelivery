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

    /**
     * 소비자 - 주문하기
     */
    @PostMapping("/api/order")
    @CustomerOnly
    public ResponseEntity<OrderResponse> createOrder(@CurrentUser UserPrincipal user, @RequestBody @Valid OrderRequest request) {
        OrderResponse order = orderService.createOrder(user.toCustomer(), request);
        return ResponseEntity.ok(order);
    }

    /**
     * 소비자 - 자신의 주문내역 확인
     */
    @GetMapping("/api/order/history")
    @CustomerOnly
    public void getOrderHistory() {

    }

    /**
     * 소비자 - 주문 취소
     */
    @PatchMapping("/api/order/{orderId}/cancel")
    @CustomerOnly
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 판매자 - 내 가게의 주문내역 확인(상태별로 : default : 진행중인 주문)
     */
    @GetMapping("/api/order/{shopId}")
    @SellerOnly
    public void getOrderList(@PathVariable Long shopId) {

    }

    /**
     * 판매자 - 주문 상태 변경
     */
    @PatchMapping("/api/order/{orderId}/state")
    @SellerOnly
    public void changeOrderState(@PathVariable Long orderId) {

    }

    /**
     * 판매자 - 주문 거절
     */
    @PatchMapping("/api/order/{orderId}/reject")
    @SellerOnly
    public void rejectOrder() {

    }

    /**
     * 공통 - 주문 상세 확인
     */
    @GetMapping("/api/order/{orderId}")
    public void getOrderDetail(@PathVariable Long orderId) {

    }
}
