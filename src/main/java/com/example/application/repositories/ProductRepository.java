package com.example.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.application.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
