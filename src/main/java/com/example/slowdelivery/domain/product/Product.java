package com.example.slowdelivery.domain.product;

import com.example.slowdelivery.common.domain.BaseEntity;
import com.example.slowdelivery.domain.shop.Shop;
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

    @OneToMany(mappedBy = "product")
    private List<ProductOption> options = new ArrayList<>();

    private String productName;
    private Integer productPrice;
    //재고
    @Builder
    public Product(List<ProductOption> options, String productName, Integer productPrice) {
        this.options = options;
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
