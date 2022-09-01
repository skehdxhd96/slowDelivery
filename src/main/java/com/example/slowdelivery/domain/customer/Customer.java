package com.example.slowdelivery.domain.customer;

import com.example.slowdelivery.domain.orders.Order;
import com.example.slowdelivery.user.domain.AuthProvider;
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
@PrimaryKeyJoinColumn(name = "customer_id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = "CUSTOMER")
public class Customer extends User {

    @OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;
    private String providerId;
    private String address;
    @Builder
    public Customer(String name, String nickname, String email, Role role,
                AuthProvider provider, String providerId, String address) {
        super(name, nickname, email, role, null);
        this.provider = provider;
        this.providerId = providerId;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
