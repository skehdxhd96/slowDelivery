package com.example.slowdelivery.domain.customer;

import com.example.slowdelivery.user.domain.AuthProvider;
import com.example.slowdelivery.user.domain.Role;
import com.example.slowdelivery.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = "CUSTOMER")
public class Customer extends User {

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;
    private String providerId;

    @Builder
    public Customer(String name, String nickname, String email, Role role,
                AuthProvider provider, String providerId) {
        super(name, nickname, email, role, null);
        this.provider = provider;
        this.providerId = providerId;
    }
}
