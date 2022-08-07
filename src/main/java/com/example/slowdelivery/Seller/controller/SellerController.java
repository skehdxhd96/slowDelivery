package com.example.slowdelivery.Seller.controller;

import com.example.slowdelivery.Seller.dto.SignUpRequest;
import com.example.slowdelivery.Seller.service.SellerService;
import com.example.slowdelivery.common.annotation.CurrentUser;
import com.example.slowdelivery.common.annotation.CustomerOnly;
import com.example.slowdelivery.common.annotation.SellerOnly;
import com.example.slowdelivery.security.common.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seller")
public class SellerController {

    private final SellerService sellerService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public void signUp(@Valid @RequestBody SignUpRequest request) {

        request.setPassword(passwordEncoder.encode(request.getPassword()));
        sellerService.signUp(request);
    }
}
