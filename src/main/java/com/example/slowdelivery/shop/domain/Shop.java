package com.example.slowdelivery.shop.domain;

import com.example.slowdelivery.Seller.domain.Seller;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Shop {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Seller seller;

    private String phone;
    private String shopName;
    private Integer minimum_price;
    private openStatus openStatus;

    @Builder
    public Shop(String phone, String shopName, int minimum_price, com.example.slowdelivery.shop.domain.openStatus openStatus) {
        this.phone = phone;
        this.shopName = shopName;
        this.minimum_price = minimum_price;
        this.openStatus = openStatus;
    }

    public boolean isOpen() {
        return (this.openStatus == openStatus.OPEN) ? true : false;
    }
}
