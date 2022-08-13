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
    public void addProduct(@PathVariable Long shopId, @RequestBody @Valid ProductRequest request) {
        // 그 전에서 자신의 가게인걸 체크하고 왔다고 진행해야하나??
        productService.addProduct(shopId, request);
    }

    @GetMapping("/shop/{shopId}/product/{productId}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long shopId, @PathVariable Long productId) {
        ProductResponse product = productService.findProduct(productId);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/shop/{shopId}/product/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long shopId, @PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/shop/{shopId}/product/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long shopId, @PathVariable Long productId, @RequestBody @Valid ProductRequest request) {
        productService.updateProduct(productId, request);
        return ResponseEntity.noContent().build();
    }
}
