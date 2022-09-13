package com.example.slowdelivery.dto.order;

import com.example.slowdelivery.domain.cart.Cart;
import com.example.slowdelivery.domain.cart.CartItem;
import com.example.slowdelivery.domain.cart.CartItemOption;
import com.example.slowdelivery.domain.customer.Customer;
import com.example.slowdelivery.domain.orders.*;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public OrderRequest(String orderType, String msg, String deliveryAddress, String payway) {
        this.orderType = orderType;
        this.msg = msg;
        this.deliveryAddress = deliveryAddress;
        this.payway = payway;
    }

    public Order moveCartToOrder(Cart myCart, Customer customer) {

        Order newOrder = Order.builder()
                .customer(customer)
                .msg(msg)
                .deliveryAddress(deliveryAddress)
                .orderType(OrderType.convert(orderType))
                .orderStatus(OrderStatus.WAITING)
                .build();

        List<OrderItem> items = myCart.getCartItems().stream().map(i -> OrderItem.builder()
                            .quantity(i.getQuantity())
                            .orderItemOptions(i.getOptions().stream().map(o -> OrderItemOption.builder()
                                                                                    .optionId(o.getProductOptionId())
                                                                                    .optionName(o.getProductOptionName())
                                                                                    .optionPrice(o.getProductOptionPrice())
                                                                                    .build())
                                                                                .collect(Collectors.toList()))
                            .productPrice(i.getProductPrice())
                            .productId(i.getProductId())
                            .productName(i.getProductName())
                            .build())
        .collect(Collectors.toList());

        for (OrderItem item : items) {
            for (OrderItemOption orderItemOption : item.getOrderItemOptions())
                item.moveCartOptionToOrder(orderItemOption);
            newOrder.moveOrderItemByCart(item);
        }

        newOrder.setTotalPrice();

        return newOrder;
    }
}

