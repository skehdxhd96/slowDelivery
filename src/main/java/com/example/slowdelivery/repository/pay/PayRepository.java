package com.example.slowdelivery.repository.pay;

import com.example.slowdelivery.domain.pay.Pay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayRepository extends JpaRepository<Pay, Long> , PayRepositoryCustom{
}
