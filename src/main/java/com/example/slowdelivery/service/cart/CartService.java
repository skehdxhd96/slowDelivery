package com.example.slowdelivery.service.cart;

import com.example.slowdelivery.domain.cart.Cart;
import com.example.slowdelivery.domain.customer.Customer;
import com.example.slowdelivery.dto.cart.CartRequest;
import com.example.slowdelivery.repository.cart.CartRepository;
import com.example.slowdelivery.utils.RedisKeyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.example.slowdelivery.utils.RedisKeyFactory.*;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    @Transactional
    public void addProduct(Customer customer, CartRequest request) {
        String cartId = generateCartId(customer.getId());
        Cart cart = request.toEntity(cartId);
    }

    private String validate(Long userId) {
        boolean userHasCart = cartRepository.validate(userId);
        if(!userHasCart) {
            return generateCartId(userId);
        }

    }
}
