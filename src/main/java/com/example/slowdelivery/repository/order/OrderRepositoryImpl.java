package com.example.slowdelivery.repository.order;

import com.example.slowdelivery.domain.orders.Order;
import com.example.slowdelivery.domain.orders.OrderStatus;
import com.example.slowdelivery.domain.orders.OrderType;
import com.example.slowdelivery.dto.order.OrderFindRequest;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import static com.example.slowdelivery.domain.orders.QOrder.order;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Order> findByShopIdWithOrderStatus(Long shopId, OrderFindRequest request) {
        return queryFactory.select(order)
                .from(order)
                .where(setOrderStatus(request)
                        .and(order.shopId.eq(shopId)))
                .fetch();
    }

    @Override
    public List<Order> findByCustomerIdWithOrderStatus(Long customerId, OrderFindRequest request) {
        return queryFactory.select(order)
                .from(order)
                .where(setOrderStatus(request)
                        .and(order.customer.id.eq(customerId)))
                .fetch();
    }

    @Override
    public List<Order> findSlowOrderListWithAddressAndTime(String address, LocalDateTime reservationTime, Long shopId) {
        return queryFactory.select(order)
                .from(order)
                .where(order.orderType.eq(OrderType.SLOW_DELIVERY)
                        .and(order.shopId.eq(shopId))
                        .and(order.deliveryAddress.eq(address))
                        .and(order.reservationTime.eq(reservationTime))
                        .and(order.orderStatus.eq(OrderStatus.READY)))
                .orderBy(order.id.asc())
                .fetch();
    }

    private BooleanExpression setOrderStatus(OrderFindRequest request) {

        if(request.getOrderStatus().equals("CANCEL")) {
            return order.orderStatus.eq(OrderStatus.CANCEL);
        } else if(request.getOrderStatus().equals("WAITING")) {
            return order.orderStatus.eq(OrderStatus.WAITING);
        } else if(request.getOrderStatus().equals("COMPLETE")) {
            return order.orderStatus.eq(OrderStatus.COMPLETE);
        } else if(request.getOrderStatus().equals("FAIL")) {
            return order.orderStatus.eq(OrderStatus.FAIL);
        } else if(request.getOrderStatus().equals("REJECT")) {
            return order.orderStatus.eq(OrderStatus.REJECT);
        } else {
            return order.orderStatus.eq(OrderStatus.READY);
        }
    }
}
