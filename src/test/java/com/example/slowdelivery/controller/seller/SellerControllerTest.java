package com.example.slowdelivery.controller.seller;

import com.example.slowdelivery.service.seller.SellerService;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

@WebMvcTest(controllers = SellerController.class)
public class SellerControllerTest {

    @MockBean
    SellerService sellerService;

    @MockBean
    PasswordEncoder passwordEncoder;


}
