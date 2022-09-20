package com.example.slowdelivery.repository.rider;

import com.example.slowdelivery.domain.rider.Rider;
import com.example.slowdelivery.domain.seller.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RiderRepository extends JpaRepository<Rider, Long> {
    Optional<Rider> findByEmail(String email);
}
