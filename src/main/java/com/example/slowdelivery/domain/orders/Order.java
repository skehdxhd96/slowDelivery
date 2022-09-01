package com.example.slowdelivery.domain.orders;

import com.example.slowdelivery.common.domain.BaseEntity;
import com.example.slowdelivery.domain.customer.Customer;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @Enumerated(value = EnumType.STRING)
    private OrderWay orderWay;

    @Enumerated(value = EnumType.STRING)
    private OrderType orderType;

    private String msg;
    private String deliveryAddress;
    private int deliveryTip;
    private int totalOrderPrice; // 배달비 제외

    @Builder
    public Order(Customer customer, List<Order> orders, OrderStatus orderStatus, OrderWay orderWay,
                 OrderType orderType, String msg, String deliveryAddress, int deliveryTip, int totalOrderPrice) {
        this.customer = customer;
        this.orders = orders;
        this.orderStatus = orderStatus;
        this.orderWay = orderWay;
        this.orderType = orderType;
        this.msg = msg;
        this.deliveryAddress = deliveryAddress;
        this.deliveryTip = deliveryTip;
        this.totalOrderPrice = totalOrderPrice;
    }

    // 할인금액(쿠폰)
    // 느린배달 - 예약시간
    // 주문 - 완료시간
}
