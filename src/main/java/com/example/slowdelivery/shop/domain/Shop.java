package com.example.slowdelivery.shop.domain;

import com.example.slowdelivery.Seller.domain.Seller;
import com.example.slowdelivery.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    private String phone;
    private String shopName;
    private Integer minimumPrice;

    @Enumerated(EnumType.STRING)
    private openStatus openStatus;

    @Builder
    public Shop(Seller seller, String phone, String shopName, int minimumPrice, openStatus openStatus) {
        this.seller = seller;
        this.phone = phone;
        this.shopName = shopName;
        this.minimumPrice = minimumPrice;
        this.openStatus = openStatus;
    }

    public boolean isOpen() {
        return (this.openStatus == openStatus.OPEN) ? true : false;
    }
}
