package com.example.slowdelivery.domain.orders;

import com.example.slowdelivery.common.domain.BaseEntity;
import com.example.slowdelivery.domain.product.Product;
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
public class OrderItem extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderitem_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToMany(mappedBy = "orderItem", cascade = CascadeType.ALL)
    private List<OrderItemOption> orderItemOptions = new ArrayList<>();

    private int quantity;
    @Builder
    public OrderItem(Product product, Order order, List<OrderItemOption> orderItemOptions, int quantity) {
        this.product = product;
        this.order = order;
        this.orderItemOptions = orderItemOptions;
        this.quantity = quantity;
    }

    public int getPriceAddOption() {

        int options = this.orderItemOptions.stream()
                                            .mapToInt(o -> o.getProductOption()
                                                            .getProdcutOptionPrice())
                                            .sum();

        return (this.product.getProductPrice() + options) * quantity;
    }

    public void setOrderItemToOrder() {

    }
}
