package com.example.slowdelivery.dto.seller;

import com.example.slowdelivery.domain.rider.Rider;
import com.example.slowdelivery.domain.rider.WorkStatus;
import com.example.slowdelivery.domain.seller.Seller;
import com.example.slowdelivery.user.domain.Role;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SignUpRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String nickname;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    public Seller toSeller(SignUpRequest request) {
        return Seller.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .nickname(request.getNickname())
                .role(Role.SELLER)
                .build();
    }

    public Rider toRider(SignUpRequest request) {
        return Rider.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .nickname(request.getNickname())
                .workStatus(WorkStatus.OFF)
                .role(Role.RIDER)
                .build();
    }
}
