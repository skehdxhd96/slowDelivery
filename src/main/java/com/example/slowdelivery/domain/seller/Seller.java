package com.example.slowdelivery.domain.seller;

import com.example.slowdelivery.domain.shop.Shop;
import com.example.slowdelivery.user.domain.Role;
import com.example.slowdelivery.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@PrimaryKeyJoinColumn(name = "seller_id")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@DiscriminatorValue(value = "SELLER")
public class Seller extends User {

    @OneToMany(mappedBy = "seller")
    private List<Shop> shop = new ArrayList<>();

    @Builder
    public Seller(String name, String nickname, Role role, String email, String password) {
        super(name, nickname, email, role, password);
    }
}
