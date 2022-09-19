package com.example.slowdelivery.domain.rider;

import com.example.slowdelivery.user.domain.Role;
import com.example.slowdelivery.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@PrimaryKeyJoinColumn(name = "rider_id")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@DiscriminatorValue(value = "RIDER")
public class Rider extends User {

    @Enumerated(EnumType.STRING)
    private WorkStatus workStatus;

    @Builder
    public Rider(String name, String nickname, Role role, String email, String password) {
        super(name, nickname, email, role, password);
    }
}
