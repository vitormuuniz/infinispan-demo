package com.example.application.services;

import java.util.List;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.application.config.KafkaProducer;
import com.example.application.exceptions.ProductException;
import com.example.application.models.Product;
import com.example.application.repositories.ProductRepository;

@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final RemoteCache<Long, Product> cache;
    private final KafkaProducer kafkaProducer;

    public ProductService(ProductRepository productRepository, RemoteCacheManager remoteCacheManager, KafkaProducer kafkaProducer) {
        this.productRepository = productRepository;

        cache = remoteCacheManager.administration().getOrCreateCache("products", new ConfigurationBuilder().build());
        this.kafkaProducer = kafkaProducer;
    }

    public Product save(Product product) {
        Product productDB = productRepository.save(product);
        cache.put(productDB.getId(), productDB);

        kafkaProducer.sendMessage("example-topic", productDB.toString());
        return productDB;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        Product product = cache.get(id);

        if (product != null) {
            LOGGER.info("Getting product from cache");
            return product;
        }

        product = productRepository.findById(id).orElseThrow(() -> new ProductException("Product not found", HttpStatus.NOT_FOUND));

        cache.put(id, product);

        LOGGER.info("Getting product from database");

        return product;
    }
}
