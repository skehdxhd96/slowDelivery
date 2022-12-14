package com.example.slowdelivery.dto.order;

import com.example.slowdelivery.domain.cart.Cart;
import com.example.slowdelivery.domain.customer.Customer;
import com.example.slowdelivery.domain.orders.*;
import com.example.slowdelivery.domain.shop.Shop;
import lombok.Builder;
import lombok.Getter;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
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
    private LocalDateTime reservationTime;

    @Builder
    public OrderRequest(String orderType, String msg, String deliveryAddress, String payway, LocalDateTime reservationTime) {
        this.orderType = orderType;
        this.msg = msg;
        this.deliveryAddress = deliveryAddress;
        this.payway = payway;
        this.reservationTime = reservationTime;
    }

    public Order moveCartToOrder(Cart myCart, Customer customer, Shop shop) {

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

        Order newOrder = Order.builder()
                .customer(customer)
                .msg(msg)
                .deliveryAddress(deliveryAddress)
                .orderType(OrderType.convert(orderType))
                .orderStatus(OrderStatus.WAITING)
                .shopId(shop.getId())
                .shopName(shop.getShopName())
                .reservationTime(reservationTime) // null?
                .build();

        for (OrderItem item : items) {
            for (OrderItemOption orderItemOption : item.getOrderItemOptions())
                item.moveCartOptionToOrder(orderItemOption);
            newOrder.moveOrderItemByCart(item);
        }

        newOrder.setTotalPrice();

        return newOrder;
    }
}

