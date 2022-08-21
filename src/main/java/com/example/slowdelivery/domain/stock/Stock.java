package com.example.slowdelivery.domain.stock;

import com.example.slowdelivery.exception.ErrorCode;
import com.example.slowdelivery.exception.StockException;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
import java.io.Serializable;

@RedisHash("STOCK")
@Getter
public class Stock implements Serializable {

    private static final long serialVersionUID = -9202482480547311884L;

    @Id
    private String id;

    @Indexed
    private Long productId;

    private int total;

    private int remain;

    // 등록 시에 remain은 받지 말고 total로 다 초기화?

    @Builder
    public Stock(String id, Long productId, int total, int remain) {
        this.id = id;
        this.productId = productId;
        this.total = total;
        this.remain = remain;
    }

    private static void validated(int value) {
        if(value < 1)
            throw new StockException(ErrorCode.STOCK_CANNOT_NEGATIVE);
    }
}
