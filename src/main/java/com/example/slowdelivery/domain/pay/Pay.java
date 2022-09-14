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

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Enumerated(EnumType.STRING)
    private PayStatus paystatus;

    @Enumerated(EnumType.STRING)
    private PayWay payway;

    private int totalPaymentPrice; // 배달비 + 주문금액 + 할인금액
    @Builder
    public Pay(Order order, PayStatus paystatus, PayWay payway, int totalPaymentPrice) {
        this.order = order;
        this.paystatus = paystatus;
        this.payway = payway;
        this.totalPaymentPrice = totalPaymentPrice;
    }

    public static Pay doPayByOrderReceipt(Order order, PayWay payway) {

        return Pay.builder()
                .order(order)
                .paystatus(PayStatus.WAITING)
                .payway(payway)
                .totalPaymentPrice(order.getTotalOrderPrice() + order.getDeliveryTip())
                .build();
    }

    public void success() {
        this.paystatus = PayStatus.COMPLETE;
    }

    public void fail() {
        this.paystatus = PayStatus.FAIL;
    }

    public void cancel() {
        this.paystatus = PayStatus.CANCEL;
    }
}
