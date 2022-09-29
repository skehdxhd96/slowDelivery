package com.example.slowdelivery.repository.order;

import com.example.slowdelivery.domain.orders.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {

}
