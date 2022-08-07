package com.example.slowdelivery.presentation;

import com.example.slowdelivery.common.annotation.CurrentUser;
import com.example.slowdelivery.common.annotation.CustomerOnly;
import com.example.slowdelivery.common.annotation.SellerOnly;
import com.example.slowdelivery.security.common.UserPrincipal;
import com.example.slowdelivery.user.domain.User;
import com.example.slowdelivery.user.dto.SignUpRequest;
import com.example.slowdelivery.user.repository.UserRepository;
import com.example.slowdelivery.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public void signUp(@Valid @RequestBody SignUpRequest request) {

        request.setPassword(passwordEncoder.encode(request.getPassword()));
        userService.signUp(request);
    }
}
