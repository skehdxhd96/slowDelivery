package com.example.slowdelivery.service.order;

import com.example.slowdelivery.domain.cart.Cart;
import com.example.slowdelivery.domain.customer.Customer;
import com.example.slowdelivery.domain.orders.Order;
import com.example.slowdelivery.domain.pay.PayWay;
import com.example.slowdelivery.dto.order.OrderRequest;
import com.example.slowdelivery.repository.cart.CartRepository;
import com.example.slowdelivery.repository.order.OrderRepository;
import com.example.slowdelivery.service.pay.PayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final PayService payService;
    @Transactional
    public void createOrder(Customer customer, OrderRequest request) {

        Cart myCart = cartRepository.getCartListAndDelete(customer.getId());

        cartRepository.RollbackCartOnOrder(customer.getId(), myCart);
        //TODO : Stock RollBack Hook

        //TODO : 재고확인 / 재고감소
        Order order = request.moveCartToOrder(myCart, customer);
        order.setTotalPrice();

        Order newOrder = orderRepository.save(order);

        payService.doPay(newOrder, PayWay.convert(request.getPayway()));
    }
}
