package com.example.slowdelivery.service.seller;

import com.example.slowdelivery.domain.seller.Seller;
import com.example.slowdelivery.dto.seller.SignUpRequest;
import com.example.slowdelivery.repository.seller.SellerRepository;
import com.example.slowdelivery.exception.DuplicatedException;
import com.example.slowdelivery.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        if(isPresent) {
            throw new DuplicatedException(ErrorCode.SIGNUP_DUPLICATED);
        }
    }
}
