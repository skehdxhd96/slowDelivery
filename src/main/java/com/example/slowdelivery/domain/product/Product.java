package com.example.slowdelivery.domain.product;

import com.example.slowdelivery.common.domain.BaseEntity;
import com.example.slowdelivery.domain.shop.Shop;
import com.example.slowdelivery.domain.stock.Stock;
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
public class Product extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductOption> options = new ArrayList<>();

    private String productName;
    private Integer productPrice;

    //판매 가능 상태 불가능상태 Enum

    @Builder
    public Product(Long id, List<ProductOption> options, String productName, Integer productPrice) {
        this.id = id;
        this.options = options;
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public void update(String productName, Integer productPrice) {
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public void updateShop(Shop shop) {
        this.shop = shop;
    }

    public void addOptions(final List<ProductOption> options) {
        this.options = options;

        for (ProductOption option : options) {
            option.updateProduct(this);
        }
    }
}
