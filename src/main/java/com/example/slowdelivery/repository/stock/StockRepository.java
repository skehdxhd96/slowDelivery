package com.example.slowdelivery.repository.stock;

import com.example.slowdelivery.domain.stock.Stock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface StockRepository extends CrudRepository<Stock, Long> {

    Optional<Stock> findByProductId(Long productId);
}
