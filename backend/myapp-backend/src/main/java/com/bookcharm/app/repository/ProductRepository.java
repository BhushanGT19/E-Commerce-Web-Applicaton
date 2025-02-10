package com.bookcharm.app.repository;

import com.bookcharm.app.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Additional product-specific query methods if needed
}
