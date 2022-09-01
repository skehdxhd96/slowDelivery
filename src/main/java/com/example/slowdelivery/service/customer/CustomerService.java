package com.example.slowdelivery.service.customer;

import com.example.slowdelivery.domain.customer.Customer;
import com.example.slowdelivery.dto.customer.CustomerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    @Transactional
    public void updateCurrentAddress(Customer customer, CustomerRequest request) {
        customer.setAddress(request.getAddress());
    }
}
