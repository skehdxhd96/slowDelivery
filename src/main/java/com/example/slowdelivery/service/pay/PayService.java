package com.example.slowdelivery.service.pay;

import com.example.slowdelivery.domain.orders.Order;
import com.example.slowdelivery.domain.pay.Pay;
import com.example.slowdelivery.domain.pay.PayWay;
import com.example.slowdelivery.repository.pay.PayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PayService {

    private final PayRepository payRepository;

    @Transactional
    public Pay doPay(Order order, PayWay payway) {

        Pay newPay = Pay.doPayByOrderReceipt(order, payway);
        return payRepository.save(newPay);
    }
}
