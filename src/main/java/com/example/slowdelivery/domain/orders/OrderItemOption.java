package com.example.slowdelivery.domain.orders;

import com.example.slowdelivery.common.domain.BaseEntity;
import com.example.slowdelivery.domain.product.ProductOption;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItemOption extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderitem_id")
    private OrderItem orderItem;
    private Long optionId;
    private String optionName;
    private int optionPrice;

    @Builder
    public OrderItemOption(OrderItem orderItem, Long optionId, String optionName, int optionPrice) {
        this.orderItem = orderItem;
        this.optionId = optionId;
        this.optionName = optionName;
        this.optionPrice = optionPrice;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }
}
