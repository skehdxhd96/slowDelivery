package com.example.slowdelivery.Customer.repository;

import com.example.slowdelivery.Customer.domain.Customer;
import com.example.slowdelivery.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<User> findByEmail(String email);
}