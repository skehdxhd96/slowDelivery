package com.example.slowdelivery.domain.orders;

import com.example.slowdelivery.common.domain.BaseEntity;
import com.example.slowdelivery.domain.customer.Customer;
import com.example.slowdelivery.domain.pay.Pay;
import com.example.slowdelivery.exception.ErrorCode;
import com.example.slowdelivery.exception.OrderException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order")
    private List<Pay> pays = new ArrayList<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @Enumerated(value = EnumType.STRING)
    private OrderType orderType;

    private Long shopId;
    private Long riderId;
    private String shopName;
    private String msg;
    private String deliveryAddress;
    private int deliveryTip;
    private int totalOrderPrice;
    private LocalDateTime reservationTime;
    private LocalDateTime orderCompleteTime;

    @Builder
    public Order(Customer customer, List<Pay> pays, List<OrderItem> items, OrderStatus orderStatus, OrderType orderType, Long shopId,
                 Long riderId, String shopName, String msg, String deliveryAddress, int deliveryTip, int totalOrderPrice, LocalDateTime reservationTime,
                 LocalDateTime orderCompleteTime) {
        this.customer = customer;
        this.pays = pays;
        this.items = items;
        this.orderStatus = orderStatus;
        this.orderType = orderType;
        this.shopId = shopId;
        this.riderId = riderId;
        this.shopName = shopName;
        this.msg = msg;
        this.deliveryAddress = deliveryAddress;
        this.deliveryTip = deliveryTip;
        this.totalOrderPrice = totalOrderPrice;
        this.reservationTime = reservationTime;
        this.orderCompleteTime = orderCompleteTime;
    }

    public void moveOrderItemByCart(OrderItem orderItem) {
        this.items.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setTotalPrice() {
        this.totalOrderPrice = this.items.stream().mapToInt(i -> i.getPriceAddOption()).sum();
    }

    public void setDeliveryTip(int tip, int people) {

        if(this.orderType == OrderType.SLOW_DELIVERY)
            this.deliveryTip = tip / people;
        else this.deliveryTip = tip;
    }

    public void cancelOrder() {

        if(this.orderStatus != OrderStatus.WAITING)
            throw new OrderException(ErrorCode.CANNOT_CANCEL_ORDER);

        this.orderStatus = OrderStatus.CANCEL;
    }

    public void DoneOrder() {
        if(this.getOrderStatus() == OrderStatus.DELIVERY_ING)
            this.orderStatus = OrderStatus.COMPLETE;
    }

    public void changeOrderStatusToDeliveryRequest() {

        if(this.getOrderStatus() == OrderStatus.READY)
            this.orderStatus = OrderStatus.DELIVERY_REQUEST;
    }

    public void changeOrderStatusToDeliveryIng() {
        if(this.getOrderStatus() == OrderStatus.DELIVERY_REQUEST)
            this.orderStatus = OrderStatus.DELIVERY_ING;
    }

    public void changeOrderStatusBackToReady() {
        if(this.getOrderStatus() == OrderStatus.DELIVERY_REQUEST)
            this.orderStatus = OrderStatus.READY;
    }

    public void rejectOrder() {
        if(this.getOrderStatus() == OrderStatus.WAITING)
            this.orderStatus = OrderStatus.REJECT;
    }

    public void changeOrderStatusToReady(LocalDateTime reservationTime) {

        if(this.orderType == OrderType.GENERAL_DELIVERY)
            this.reservationTime = reservationTime;

        if(this.getOrderStatus() == OrderStatus.WAITING)
            this.orderStatus = OrderStatus.READY;
    }

    public void changeOrderStatusToFail() {
        if(this.getOrderStatus() == OrderStatus.WAITING)
            this.orderStatus = OrderStatus.FAIL;
    }

    public void registerRider(Long riderId) {
        this.riderId = riderId;
    }
}
