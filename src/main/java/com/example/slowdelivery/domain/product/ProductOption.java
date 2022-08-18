package com.example.slowdelivery.domain.product;

import com.example.slowdelivery.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOption extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productOption_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private String productOptionName;
    private Integer prodcutOptionPrice;

    // 옵션 추가 가능 여부

    // 옵션 수량 : 여기는 필요 없고 장바구니에 있어야 함

    // 옵션 재고

    public ProductOption(String productOptionName, Integer prodcutOptionPrice) {
        this.productOptionName = productOptionName;
        this.prodcutOptionPrice = prodcutOptionPrice;
    }

    public void updateProduct(Product product) {
        this.product = product;
    }
}
