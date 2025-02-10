package com.bookcharm.app.repository;

import com.bookcharm.app.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    // You can add custom query methods if needed
}
