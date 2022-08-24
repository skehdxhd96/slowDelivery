package com.example.slowdelivery.controller.cart;

import com.example.slowdelivery.common.annotation.CurrentUser;
import com.example.slowdelivery.common.annotation.CustomerOnly;
import com.example.slowdelivery.dto.cart.CartItemRequest;
import com.example.slowdelivery.dto.cart.CartRequest;
import com.example.slowdelivery.security.common.UserPrincipal;
import com.example.slowdelivery.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/api/cart")
    @CustomerOnly
    public void addProduct(@CurrentUser UserPrincipal user, @RequestBody @Valid CartRequest request) {

        cartService.addProduct(user.toCustomer(), request);
    }
}
