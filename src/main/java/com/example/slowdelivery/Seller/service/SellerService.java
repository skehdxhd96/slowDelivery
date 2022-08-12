package com.example.slowdelivery.Seller.service;

import com.example.slowdelivery.Customer.domain.Customer;
import com.example.slowdelivery.Seller.domain.Seller;
import com.example.slowdelivery.Seller.dto.SignUpRequest;
import com.example.slowdelivery.Seller.repository.SellerRepository;
import com.example.slowdelivery.exception.DuplicatedException;
import com.example.slowdelivery.exception.ErrorCode;
import com.example.slowdelivery.user.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SellerService {

    private final SellerRepository sellerRepository;

    @Transactional
    public Long signUp(SignUpRequest request) {

        validateSeller(request);
        Seller seller = request.toEntity(request);
        Seller signUpSeller = sellerRepository.save(seller);

        return signUpSeller.getId();
    }

    private void validateSeller(SignUpRequest request) {
        boolean isPresent = sellerRepository.findByEmail(request.getEmail()).isPresent();
        if(!isPresent) {
            throw new DuplicatedException(ErrorCode.SIGNUP_DUPLICATED);
        }
    }
}
