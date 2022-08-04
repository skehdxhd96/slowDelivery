package com.example.slowdelivery.presentation;

import com.example.slowdelivery.common.annotation.CurrentUser;
import com.example.slowdelivery.common.annotation.CustomerOnly;
import com.example.slowdelivery.common.annotation.SellerOnly;
import com.example.slowdelivery.user.domain.User;
import com.example.slowdelivery.user.dto.SignUpRequest;
import com.example.slowdelivery.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public void signUp(@Valid @RequestBody SignUpRequest request) {

        request.setPassword(passwordEncoder.encode(request.getPassword()));
        userService.signUp(request);
    }

    @GetMapping("/currentusertest")
    public String currentusertest(@CurrentUser User currentUser) {

        return currentUser.getName();
    }

    @SellerOnly
    @GetMapping("/sellertest")
    public String sellertest(@CurrentUser User currentUser) {

        return currentUser.getName();
    }

    @CustomerOnly
    @GetMapping("/customertest")
    public String customertest(@CurrentUser User currentUser) {

        return currentUser.getName();
    }


}
