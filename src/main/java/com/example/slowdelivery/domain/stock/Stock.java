package com.example.slowdelivery.domain.stock;

import com.example.slowdelivery.common.domain.BaseEntity;
import com.example.slowdelivery.exception.ErrorCode;
import com.example.slowdelivery.exception.StockException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Stock extends BaseEntity {

    /**
     * 재고 마스터 테이블
     * 관계 미설정 : 직접 접근 불가능(다른 도메인 객체를 이용해 접근 가능)
     * 사용 재고의 경우 Redis에 저장
     */

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private Long id;

    @NotNull
    @Column(nullable = false)
    private int total;

    @NotNull
    @Column(nullable = false)
    private int remain;

    public final void syncCurrent(final int value) {
        this.remain = value;
    }

    private static void validated(int value) {
        if(value < 1)
            throw new StockException(ErrorCode.STOCK_CANNOT_NEGATIVE);
    }

}
