package com.example.slowdelivery.user.domain;

import com.example.slowdelivery.common.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "Role")
public abstract class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name;
    private String nickname;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "Role", insertable = false)
    private Role role;

    public User(String name, String nickname, String email, Role role, String password) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.role = role;
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String roleName() {
        return this.role.toString();
    }
}
