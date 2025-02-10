package com.bookcharm.app.repository;

import com.bookcharm.app.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Additional order-specific query methods if needed
}
