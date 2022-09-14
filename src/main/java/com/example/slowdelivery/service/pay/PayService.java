package com.example.slowdelivery.service.pay;

import com.example.slowdelivery.domain.orders.Order;
import com.example.slowdelivery.domain.pay.Pay;
import com.example.slowdelivery.domain.pay.PayWay;
import com.example.slowdelivery.exception.ErrorCode;
import com.example.slowdelivery.exception.PayException;
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

    @Transactional
    public void failPay(Order order) {
        Pay pay = payRepository.findByOrderIdWithWaitingStatus(order.getId())
                    .orElseThrow(() -> new PayException(ErrorCode.PAY_NOT_FOUND));

        pay.fail();
    }

    @Transactional
    public void successPay(Order order) {
        Pay pay = payRepository.findByOrderIdWithWaitingStatus(order.getId())
                    .orElseThrow(() -> new PayException(ErrorCode.PAY_NOT_FOUND));

        pay.success();
    }

    @Transactional
    public void cancelPay(Order order) {
        Pay pay = payRepository.findByOrderIdWithCompleteStatus(order.getId())
                    .orElseThrow(() -> new PayException(ErrorCode.PAY_NOT_FOUND));

        pay.cancel();
    }

    public Pay findPayWithOrder(Long orderId) {
        return payRepository.findByOrderIdFetch(orderId)
                .orElseThrow(() -> new PayException(ErrorCode.PAY_NOT_FOUND));
    }
}
