package com.example.slowdelivery.domain.pay;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PayStatus {

    WAITING("WAITING", "결제대기"),
    CANCEL("CANCEL", "결제취소"),
    COMPLETE("COMPLETE", "결제완료"),
    FAIL("FAIL", "결제실패");

    private final String key;
    private final String value;
}
