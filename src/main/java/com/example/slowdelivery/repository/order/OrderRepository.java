package com.example.slowdelivery.repository.order;

import com.example.slowdelivery.domain.orders.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
