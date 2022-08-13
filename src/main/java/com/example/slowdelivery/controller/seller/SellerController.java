package com.example.slowdelivery.controller.seller;

import com.example.slowdelivery.dto.seller.SignUpRequest;
import com.example.slowdelivery.service.seller.SellerService;
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
