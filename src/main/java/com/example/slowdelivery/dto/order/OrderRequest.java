package com.example.slowdelivery.dto.order;

import com.example.slowdelivery.domain.cart.Cart;
import com.example.slowdelivery.domain.customer.Customer;
import com.example.slowdelivery.domain.orders.*;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class OrderRequest {

    @NotBlank
    private String orderType;
    @NotBlank
    private String msg;
    @NotBlank
    private String deliveryAddress;
    @NotBlank
    private String payway;

    @Builder
    public OrderRequest(String orderType, String msg, String deliveryAddress) {
        this.orderType = orderType;
        this.msg = msg;
        this.deliveryAddress = deliveryAddress;
    }

    public Order moveCartToOrder(Cart myCart, Customer customer) {
        Order newOrder = Order.builder()
                .customer(customer)
                .msg(msg)
                .deliveryAddress(deliveryAddress)
                .orderType(OrderType.convert(orderType))
                .orderStatus(OrderStatus.WAITING)
                .build();

        myCart.getCartItems().stream().map(c -> OrderItem.builder()
                                                        .product(c.get)
                                                        .order(newOrder)
                                                        .orderItemOptions()
                                                        .quantity(c.getQuantity())
                                                        .build());

        // tip : 가게에서 가져와야 함
        // items : 기존과 똑같이 작성
    }
}

