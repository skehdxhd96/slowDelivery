package com.example.slowdelivery.repository.pay;

import com.example.slowdelivery.domain.pay.Pay;

import java.util.Optional;

public interface PayRepositoryCustom {

    Optional<Pay> findByOrderIdWithWaitingStatus(Long orderId);
    Optional<Pay> findByOrderIdWithCompleteStatus(Long orderId);
    Optional<Pay> findByOrderIdFetch(Long orderId);
}
