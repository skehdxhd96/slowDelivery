package com.example.slowdelivery.Seller.service;

import com.example.slowdelivery.Customer.domain.Customer;
import com.example.slowdelivery.Seller.domain.Seller;
import com.example.slowdelivery.Seller.dto.SignUpRequest;
import com.example.slowdelivery.Seller.repository.SellerRepository;
import com.example.slowdelivery.user.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SellerService {

    private final SellerRepository sellerRepository;

    @Transactional
    public Long signUp(SignUpRequest request) {

        Seller seller = Seller.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .nickname(request.getNickname())
                .role(Role.SELLER)
                .build();

        Seller signUpSeller = sellerRepository.save(seller);

        return signUpSeller.getId();
    }
}
