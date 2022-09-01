package com.example.slowdelivery.domain.orders;

import com.example.slowdelivery.common.domain.BaseEntity;
import com.example.slowdelivery.domain.product.ProductOption;
import lombok.AccessLevel;
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
    @JoinColumn(name = "productOption_id")
    private ProductOption productOption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderitem_id")
    private OrderItem orderItem;
}
