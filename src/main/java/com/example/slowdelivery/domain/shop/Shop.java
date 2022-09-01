package com.example.slowdelivery.domain.shop;

import com.example.slowdelivery.domain.seller.Seller;
import com.example.slowdelivery.common.domain.BaseEntity;
import com.example.slowdelivery.domain.product.Product;
import com.example.slowdelivery.exception.ErrorCode;
import com.example.slowdelivery.exception.ShopException;
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
public class Shop extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @OneToMany(mappedBy = "shop")
    private List<Product> products = new ArrayList<>();

    private String phone;
    private String shopName;
    private Integer minimumPrice;
    private Integer deliveryTip;

    @Enumerated(EnumType.STRING)
    private openStatus openStatus;

    @Builder
    public Shop(Seller seller, String phone, String shopName, int minimumPrice, openStatus openStatus, int deliveryTip) {
        this.seller = seller;
        this.phone = phone;
        this.shopName = shopName;
        this.minimumPrice = minimumPrice;
        this.openStatus = openStatus;
        this.deliveryTip = deliveryTip;
    }

    public void addProduct(Product product) {
        this.products.add(product);
        product.updateShop(this);
    }
    public boolean isOpen() {
        return (this.openStatus == openStatus.OPEN) ? true : false;
    }

    public void shopOpenValidated() {
        if(this.openStatus != openStatus.OPEN) {
            throw new ShopException(ErrorCode.SHOP_NOT_OPEN);
        }
    }
    public void biggerThanMinimumPriceValidated(int totalPrice) {
        if(this.minimumPrice > totalPrice) {
            throw new ShopException(ErrorCode.MINIMUMPRICE_UNDER);
        }
    }
}
