package com.example.slowdelivery.controller.cart;

import com.example.slowdelivery.common.annotation.CurrentUser;
import com.example.slowdelivery.common.annotation.CustomerOnly;
import com.example.slowdelivery.dto.cart.CartItemRequest;
import com.example.slowdelivery.security.common.UserPrincipal;
import com.example.slowdelivery.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/api/cart")
    @CustomerOnly
    public void addProduct(@CurrentUser UserPrincipal user, @RequestBody @Valid CartItemRequest request) {
        cartService.addProduct(user.toCustomer(), request);
    }

    @GetMapping("/api/cart")
    @CustomerOnly
    public void getCartList(@CurrentUser UserPrincipal user) {
        cartService.getCartList(user.toCustomer());
    }

    @DeleteMapping("/api/cart/{productId}")
    @CustomerOnly
    public void deleteProduct(@CurrentUser UserPrincipal user, @PathVariable Long productId) {
        cartService.deleteProduct(user.toCustomer(), productId);
    }
}
