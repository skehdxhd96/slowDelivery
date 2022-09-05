package com.example.slowdelivery.domain.pay;

import com.example.slowdelivery.common.domain.BaseEntity;
import com.example.slowdelivery.domain.orders.Order;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pay extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pay_id")
    private Long id;

    @OneToMany
    @JoinColumn(name = "order_id")
    private List<Order> orders = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private PayStatus paystatus;

    @Enumerated(EnumType.STRING)
    private PayWay payway;

    private int totalPaymentPrice; // 배달비 + 주문금액 + 할인금액

    @Builder
    public Pay(List<Order> orders, PayStatus paystatus, PayWay payway, int totalPaymentPrice) {
        this.orders = orders;
        this.paystatus = paystatus;
        this.payway = payway;
        this.totalPaymentPrice = totalPaymentPrice;
    }

    public static Pay doPayByOrderReceipt(Order order, PayWay payway) {

        List<Order> orderList = new ArrayList<>();
        orderList.add(order);

        return Pay.builder()
                .orders(orderList)
                .paystatus(PayStatus.WAITING)
                .payway(payway)
                .totalPaymentPrice(order.getTotalOrderPrice() + order.getDeliveryTip())
                .build();
    }
}
