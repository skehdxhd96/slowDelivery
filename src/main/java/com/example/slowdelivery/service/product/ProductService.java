package com.example.slowdelivery.service.product;

import com.example.slowdelivery.domain.product.Product;
import com.example.slowdelivery.domain.product.ProductOption;
import com.example.slowdelivery.domain.shop.Shop;
import com.example.slowdelivery.domain.stock.Stock;
import com.example.slowdelivery.dto.product.ProductOptionRequest;
import com.example.slowdelivery.dto.product.ProductRequest;
import com.example.slowdelivery.dto.product.ProductResponse;
import com.example.slowdelivery.exception.ErrorCode;
import com.example.slowdelivery.exception.ProductException;
import com.example.slowdelivery.exception.ShopException;
import com.example.slowdelivery.repository.product.ProductOptionRepository;
import com.example.slowdelivery.repository.product.ProductRepository;
import com.example.slowdelivery.repository.shop.ShopRepository;

import com.example.slowdelivery.repository.stock.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ShopRepository shopRepository;
    private final StockRepository stockRepository;

    @Transactional
    public void addProduct(Long shopId, ProductRequest request) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ShopException(ErrorCode.SHOP_NOT_FOUND));
        Product product = request.toEntity();

        shop.addProduct(product);
        product.addOptions(ProductOptionRequest.toList(request.getOptions()));

        Product saveProduct = productRepository.save(product);
        stockRepository.setStock(saveProduct.getId(), request.getStock());
    }

    @Transactional(readOnly = true)
    public ProductResponse findProduct(Long productId) {

        int stock = stockRepository.getStock(productId);
        Stock.validated(stock);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(ErrorCode.PRODUCT_NOT_FOUND));

        ProductResponse response = ProductResponse.of(product);
        response.setStock(stock);

        return response;
    }

    @Transactional
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    @Transactional
    public void updateProduct(Long productId, ProductRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(ErrorCode.PRODUCT_NOT_FOUND));

        product.update(request.getProductName(), request.getProductPrice());
        List<ProductOption> options = ProductOptionRequest.toList(request.getOptions());
        product.addOptions(options);

        productOptionRepository.deleteByProductId(productId);
        productOptionRepository.saveAll(options);
    }
}
