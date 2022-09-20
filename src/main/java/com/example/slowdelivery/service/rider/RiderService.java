package com.example.slowdelivery.service.rider;

import com.example.slowdelivery.domain.rider.Rider;
import com.example.slowdelivery.domain.seller.Seller;
import com.example.slowdelivery.dto.seller.SignUpRequest;
import com.example.slowdelivery.exception.DuplicatedException;
import com.example.slowdelivery.exception.ErrorCode;
import com.example.slowdelivery.repository.rider.RiderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RiderService {

    private final RiderRepository riderRepository;

    @Transactional
    public Long signUp(SignUpRequest request) {

        validateSeller(request);
        Rider rider = request.toRider(request);
        Rider signUpRider = riderRepository.save(rider);

        return signUpRider.getId();
    }

    private void validateSeller(SignUpRequest request) {
        boolean isPresent = riderRepository.findByEmail(request.getEmail()).isPresent();
        if(isPresent) {
            throw new DuplicatedException(ErrorCode.SIGNUP_DUPLICATED);
        }
    }
}
