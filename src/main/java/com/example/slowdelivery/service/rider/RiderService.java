package com.example.slowdelivery.service.rider;

import com.example.slowdelivery.domain.customer.Customer;
import com.example.slowdelivery.domain.rider.Rider;
import com.example.slowdelivery.domain.rider.WorkStatus;
import com.example.slowdelivery.domain.seller.Seller;
import com.example.slowdelivery.dto.customer.CustomerRequest;
import com.example.slowdelivery.dto.rider.RiderRequest;
import com.example.slowdelivery.dto.seller.SignUpRequest;
import com.example.slowdelivery.exception.DuplicatedException;
import com.example.slowdelivery.exception.ErrorCode;
import com.example.slowdelivery.repository.rider.RiderDeliveryRepository;
import com.example.slowdelivery.repository.rider.RiderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RiderService {

    private final RiderRepository riderRepository;
    private final RiderDeliveryRepository riderDeliveryRepository;

    @Transactional
    public Long signUp(SignUpRequest request) {

        validateRider(request);
        Rider rider = request.toRider(request);
        Rider signUpRider = riderRepository.save(rider);

        return signUpRider.getId();
    }

    @Transactional
    public void setOnAndOff(Long riderId) {
        Rider rider = riderRepository.findById(riderId)
                .orElseThrow(() -> new UsernameNotFoundException("회원을 찾을 수 없습니다."));

        rider.setOnAndOff();

        if(rider.getWorkStatus() == WorkStatus.ON) standBy(rider);
        else riderDeliveryRepository.deliveryOff(rider);
    }

    @Transactional
    public void updateRiderAddress(Rider rider, RiderRequest request) {
        rider.setAddress(request.getAddress());
    }

    private void validateRider(SignUpRequest request) {
        boolean isPresent = riderRepository.findByEmail(request.getEmail()).isPresent();
        if(isPresent) {
            throw new DuplicatedException(ErrorCode.SIGNUP_DUPLICATED);
        }
    }

    public void standBy(Rider rider) {
        riderDeliveryRepository.deliveryOn(rider);
    }
}
