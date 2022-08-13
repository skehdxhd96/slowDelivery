package com.example.slowdelivery.controller.product;

import com.example.slowdelivery.common.annotation.CurrentUser;
import com.example.slowdelivery.common.annotation.SellerOnly;
import com.example.slowdelivery.dto.product.ProductRequest;
import com.example.slowdelivery.dto.product.ProductResponse;
import com.example.slowdelivery.security.common.UserPrincipal;
import com.example.slowdelivery.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    //상품 추가 ( 전체추가도? )
    @PostMapping("/shop/{shopId}/product")
    @SellerOnly
    public void addProduct(@PathVariable Long shopId, @Valid ProductRequest request) {
        // 그 전에서 자신의 가게인걸 체크하고 왔다고 진행해야하나??
        productService.addProduct(shopId, request);
    }

    //상품 상세 조회( + 상품 옵션)
    @GetMapping("/shop/{shopId}/product/{productId}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long shopId, @PathVariable Long productId) {
        ProductResponse product = productService.findProduct(productId);
        return ResponseEntity.ok(product);
    }

    /**
     * 상품 삭제
     * 상품 수정( + 상품 옵션)
     * 상품 옵션 수정(delete and insert)
     * 상품 옵션 삭제
     * 상품 옵션 추가
     */
}
