package com.example.slowdelivery.repository.pay;

import com.example.slowdelivery.domain.pay.Pay;
import com.example.slowdelivery.domain.pay.PayStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.example.slowdelivery.domain.pay.QPay.pay;

@RequiredArgsConstructor
public class PayRepositoryImpl implements PayRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Pay> findByOrderIdWithWaitingStatus(Long orderId) {
        return Optional.ofNullable(queryFactory.select(pay)
                .from(pay)
                .where(pay.order.id.eq(orderId)
                        .and(pay.paystatus.eq(PayStatus.WAITING)))
                .fetchOne());
    }

    @Override
    public Optional<Pay> findByOrderIdWithCompleteStatus(Long orderId) {
        return Optional.ofNullable(queryFactory.select(pay)
                .from(pay)
                .where(pay.order.id.eq(orderId)
                        .and(pay.paystatus.eq(PayStatus.COMPLETE)))
                .fetchOne());
    }
}
