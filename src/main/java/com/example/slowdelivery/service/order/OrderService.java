package com.example.slowdelivery.service.order;

import com.example.slowdelivery.domain.cart.Cart;
import com.example.slowdelivery.domain.cart.CartItem;
import com.example.slowdelivery.domain.customer.Customer;
import com.example.slowdelivery.domain.orders.Order;
import com.example.slowdelivery.domain.pay.Pay;
import com.example.slowdelivery.domain.pay.PayWay;
import com.example.slowdelivery.domain.product.Product;
import com.example.slowdelivery.domain.shop.Shop;
import com.example.slowdelivery.dto.order.OrderRequest;
import com.example.slowdelivery.dto.order.OrderResponse;
import com.example.slowdelivery.dto.stock.StockResponse;
import com.example.slowdelivery.exception.ErrorCode;
import com.example.slowdelivery.exception.ProductException;
import com.example.slowdelivery.exception.ShopException;
import com.example.slowdelivery.repository.cart.CartRepository;
import com.example.slowdelivery.repository.order.OrderRepository;
import com.example.slowdelivery.repository.product.ProductRepository;
import com.example.slowdelivery.repository.shop.ShopRepository;
import com.example.slowdelivery.repository.stock.StockRepository;
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
    private final StockRepository stockRepository;
    private final ShopRepository shopRepository;

    @Transactional
    public OrderResponse createOrder(Customer customer, OrderRequest request) {

        Cart myCart = cartRepository.getCartListAndDelete(customer.getId());
        Shop shop = shopRepository.findById(myCart.getCartItems().get(0).getShopId())
                .orElseThrow(() -> new ShopException(ErrorCode.SHOP_NOT_FOUND));

        Order order = request.moveCartToOrder(myCart, customer);
        order.setDeliveryTip(shop.getDeliveryTip());

        shop.validated(order.getTotalOrderPrice());

        cartRepository.RollbackCartOnOrder(customer.getId(), myCart);
        for (CartItem cartItem : myCart.getCartItems()) {
            stockRepository.RollbackStockOnOrder(cartItem.getProductId(), cartItem.getQuantity());
            stockRepository.decreaseStock(cartItem.getProductId(), cartItem.getQuantity());
        }

        Order newOrder = orderRepository.save(order);

        Pay newPay = payService.doPay(newOrder, PayWay.convert(request.getPayway()));

        return OrderResponse.of(newOrder, newPay);
    }

    @Transactional
    public void cancelOrder() {

        /**
         * TODO :
         * 1. 재고 원복
         * 2. 장바구니 원복
         * 3. 주문 취소상태로 바꿈(삭제X)
         */

    }

    @Transactional
    public void changeOrderState() {

    }

    @Transactional
    public void rejectOrder() {

    }
}
