package com.example.slowdelivery.domain.orders;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {

    WAITING("WAITING", "주문대기"),
    CANCEL("CANCEL", "주문취소"),
    READY("READY", "주문준비중"),
    COMPLETE("COMPLETE", "주문완료"),
    FAIL("FAIL", "주문실패"),
    REJECT("REJECT", "주문거절");

    private final String key;
    private final String value;
}
