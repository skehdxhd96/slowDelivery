package com.example.slowdelivery.domain.orders;

import com.example.slowdelivery.common.domain.BaseEntity;
import com.example.slowdelivery.domain.cart.CartItem;
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
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToMany(mappedBy = "orderItem", cascade = CascadeType.ALL)
    private List<OrderItemOption> orderItemOptions = new ArrayList<>();
    private int quantity;
    private Long productId;
    private String productName;
    private int productPrice;

    @Builder
    public OrderItem(Order order, List<OrderItemOption> orderItemOptions,
                     int quantity, Long productId, String productName, int productPrice) {
        this.order = order;
        this.orderItemOptions = orderItemOptions;
        this.quantity = quantity;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public int getPriceAddOption() {

        int options = this.orderItemOptions.stream()
                                            .mapToInt(o -> o.getOptionPrice())
                                            .sum();

        return (this.productPrice + options) * quantity;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void moveCartOptionToOrder(OrderItemOption option) {
        this.orderItemOptions.add(option);
        option.setOrderItem(this);
    }
}
