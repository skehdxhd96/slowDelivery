package com.example.slowdelivery.repository.customer;

import com.example.slowdelivery.domain.customer.Customer;
import com.example.slowdelivery.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<User> findByEmail(String email);
}