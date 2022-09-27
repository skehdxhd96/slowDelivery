package com.example.slowdelivery.service.order;

import com.example.slowdelivery.domain.cart.Cart;
import com.example.slowdelivery.domain.cart.CartItem;
import com.example.slowdelivery.domain.customer.Customer;
import com.example.slowdelivery.domain.orders.Order;
import com.example.slowdelivery.domain.orders.OrderItem;
import com.example.slowdelivery.domain.orders.OrderStatus;
import com.example.slowdelivery.domain.orders.OrderType;
import com.example.slowdelivery.domain.pay.Pay;
import com.example.slowdelivery.domain.pay.PayWay;
import com.example.slowdelivery.domain.rider.Rider;
import com.example.slowdelivery.domain.shop.Shop;
import com.example.slowdelivery.dto.order.*;
import com.example.slowdelivery.exception.ErrorCode;
import com.example.slowdelivery.exception.OrderException;
import com.example.slowdelivery.exception.ShopException;
import com.example.slowdelivery.repository.cart.CartRepository;
import com.example.slowdelivery.repository.order.OrderDeliveryWaitingRepository;
import com.example.slowdelivery.repository.order.OrderRepository;
import com.example.slowdelivery.repository.shop.ShopRepository;
import com.example.slowdelivery.repository.stock.StockRepository;
import com.example.slowdelivery.service.pay.PayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final PayService payService;
    private final StockRepository stockRepository;
    private final ShopRepository shopRepository;
    private final OrderDeliveryWaitingRepository orderDeliveryWaitingRepository;

    @Transactional
    public OrderResponse createOrder(Customer customer, OrderRequest request) {

        Cart myCart = cartRepository.getCartListAndDelete(customer.getId());
        Shop shop = shopRepository.findById(myCart.getCartItems().get(0).getShopId())
                .orElseThrow(() -> new ShopException(ErrorCode.SHOP_NOT_FOUND));
        Order order = request.moveCartToOrder(myCart, customer, shop);

        order.setDeliveryTip(shop.getDeliveryTip(), shop.getDeliveryPeople());

        shop.validated(order.getTotalOrderPrice());

        cartRepository.RollbackCartOnOrder(customer.getId(), myCart);
        for (CartItem cartItem : myCart.getCartItems()) {
            stockRepository.RollbackStockOnOrder(cartItem.getProductId(), cartItem.getQuantity());
            stockRepository.decreaseStock(cartItem.getProductId(), cartItem.getQuantity());
        }

        Order newOrder = orderRepository.save(order);

        Pay newPay = payService.doPay(newOrder, PayWay.convert(request.getPayway()));

        return OrderResponse.Deatilof(newOrder, newPay);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getOrderHistory(Customer user, OrderFindRequest request) {
        List<Order> orderHistory = orderRepository.findByCustomerIdWithOrderStatus(user.getId(), request);
        return orderHistory.stream().map(OrderResponse::of).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getOrderList(Long shopId, OrderFindRequest request) {
        List<Order> orders = orderRepository.findByShopIdWithOrderStatus(shopId, request);
        return orders.stream().map(OrderResponse::of).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrderDetail(Long orderId) {
        Pay pay = payService.findPayWithOrder(orderId);
        return OrderResponse.Deatilof(pay.getOrder(), pay);
    }

    @Transactional
    public void cancelOrder(Long orderId) {

        Order order = findOrder(orderId);

        for (OrderItem item : order.getItems()) {
            stockRepository.RollbackStockOnOrderCancel(item.getProductId(), item.getQuantity());
            stockRepository.increaseStock(item.getProductId(), item.getQuantity());
        }

        order.cancelOrder();
        payService.cancelPay(order);
    }

    @Transactional
    public void rejectOrder(Long orderId) {

        Order order = findOrder(orderId);

        for (OrderItem item : order.getItems()) {
            stockRepository.RollbackStockOnOrderCancel(item.getProductId(), item.getQuantity());
            stockRepository.increaseStock(item.getProductId(), item.getQuantity());
        }

        order.rejectOrder();
        payService.cancelPay(order);
    }

    @Transactional
    public void successOrder(Long orderId, OrderUpdateRequest request) {

        Order order = findOrder(orderId);
        payService.successPay(order);
        order.changeOrderStatusToReady(request.getReservationTime());
    }

    @Transactional
    public void failOrder(Long orderId) {
        Order order = findOrder(orderId);
        payService.failPay(order);
        order.changeOrderStatusToFail();
    }

    @Transactional
    public void requestDeliveryOrder(Long orderId) {

        Order order = findOrder(orderId);

        if (order.getOrderType() == OrderType.SLOW_DELIVERY) {

            Shop shop = shopRepository.findById(order.getShopId())
                    .orElseThrow(() -> new ShopException(ErrorCode.SHOP_NOT_FOUND));
            List<Order> slowOrderList = findSlowOrderList(order.getDeliveryAddress(), order.getReservationTime(), shop.getId(), OrderStatus.READY);

            if (slowOrderList.size() < shop.getDeliveryPeople())
                throw new OrderException(ErrorCode.MINIMUM_ORDER_COUNT_UNDER);

            slowOrderList.stream().forEach(o -> o.changeOrderStatusToDeliveryRequest());

            orderDeliveryWaitingRepository.insertSlowOrderWaitingList(order.getCustomer().getAddress(), slowOrderList);
        } else {
            order.changeOrderStatusToDeliveryRequest();

            orderDeliveryWaitingRepository.insertOrderWaitingList(order.getCustomer().getAddress(), order);
        }
    }

    public List<OrderPartition> getOrderWaitingList(Rider rider) {

        Set<String> orderWaitingKeys = orderDeliveryWaitingRepository.findOrderWaitingKeys(rider.getAddress());

        return orderDeliveryWaitingRepository.findOrderWaitingList(rider.getAddress(), orderWaitingKeys)
                .stream().map(h -> (OrderPartition) h).collect(Collectors.toList());
    }

    public Order findOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalStateException("주문 정보를 불러올 때 오류가 발생했습니다."));
    }

    public List<Order> findSlowOrderList(String address, LocalDateTime reservationTime, Long shopId, OrderStatus orderStatus) {
        return orderRepository.findSlowOrderListWithAddressAndTime(address, reservationTime, shopId, orderStatus);
    }

    @Transactional
    public void requestOrderToDelivery(Long orderId, Rider rider) {

        // putifabsent 고려해봐야함( 배달중인 건이 있을때는 배달 신청 불가능 )
        Order order = findOrder(orderId);

        if (order.getOrderType() == OrderType.SLOW_DELIVERY) {
            Shop shop = shopRepository.findById(order.getShopId())
                    .orElseThrow(() -> new ShopException(ErrorCode.SHOP_NOT_FOUND));
            List<Order> slowOrderList = findSlowOrderList(order.getDeliveryAddress(), order.getReservationTime(), shop.getId(), OrderStatus.DELIVERY_REQUEST);
            orderDeliveryWaitingRepository.RollbackDeliveryRequest(order, rider, slowOrderList);
            slowOrderList.stream().forEach(o -> o.changeOrderStatusToDeliveryIng());
        } else
            order.changeOrderStatusToDeliveryIng();

        orderDeliveryWaitingRepository.standByOrderStart(order, rider);
    }

    @Transactional
    public void afterDelivery(Long orderId, Rider rider) {

        Order order = findOrder(orderId);

        if (order.getOrderType() == OrderType.SLOW_DELIVERY) {
            Shop shop = shopRepository.findById(order.getShopId())
                    .orElseThrow(() -> new ShopException(ErrorCode.SHOP_NOT_FOUND));
            List<Order> slowOrderList = findSlowOrderList(order.getDeliveryAddress(), order.getReservationTime(), shop.getId(), OrderStatus.DELIVERY_ING);

            slowOrderList.stream().forEach(o -> o.DoneOrder());
        } else order.DoneOrder();
    }
}

