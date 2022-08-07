package com.example.slowdelivery.Seller.domain;

import com.example.slowdelivery.shop.domain.Shop;
import com.example.slowdelivery.user.domain.Role;
import com.example.slowdelivery.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = "SELLER")
public class Seller extends User {

    @OneToOne(mappedBy = "seller")
    private Shop shop;

    @Builder
    public Seller(String name, String nickname, Role role, String email, String password) {
        super(name, nickname, email, role, password);
    }


}
