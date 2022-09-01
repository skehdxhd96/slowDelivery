package com.example.slowdelivery.controller.customer;

import com.example.slowdelivery.common.annotation.CurrentUser;
import com.example.slowdelivery.common.annotation.CustomerOnly;
import com.example.slowdelivery.dto.customer.CustomerRequest;
import com.example.slowdelivery.security.common.UserPrincipal;
import com.example.slowdelivery.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/api/customer/current-address")
    @CustomerOnly
    public ResponseEntity<Void> updateCurrentAddress(@CurrentUser UserPrincipal user, CustomerRequest request) {
        customerService.updateCurrentAddress(user.toCustomer(), request);
        return ResponseEntity.noContent().build();
    }
}
