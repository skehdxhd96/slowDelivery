package com.example.slowdelivery.user.service;

import com.example.slowdelivery.user.domain.Role;
import com.example.slowdelivery.user.domain.User;
import com.example.slowdelivery.user.dto.SignUpRequest;
import com.example.slowdelivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long signUp(SignUpRequest request) {

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(Role.SELLER)
                .nickname(request.getNickname())
                .build();

        User signUpUser = userRepository.save(user);

        return signUpUser.getId();
    }
}
