package com.example.slowdelivery.service.cart;

import com.example.slowdelivery.domain.cart.Cart;
import com.example.slowdelivery.domain.customer.Customer;
import com.example.slowdelivery.dto.cart.CartItemRequest;
import com.example.slowdelivery.dto.cart.CartResponse;
import com.example.slowdelivery.repository.cart.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public void addProduct(Customer customer, CartItemRequest request) {
        cartRepository.addProductToCart(customer.getId(), request.toEntity());
    }

    public CartResponse getCartList(Customer customer) {
        Cart cart = cartRepository.getCart(customer.getId());
        return CartResponse.of(cart);
    }

    public void deleteProduct(Customer customer, Long productId) {
        cartRepository.deleteProduct(customer.getId(), productId);
    }
}
