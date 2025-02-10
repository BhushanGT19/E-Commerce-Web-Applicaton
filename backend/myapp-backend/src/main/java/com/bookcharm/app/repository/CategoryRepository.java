package com.bookcharm.app.repository;

import com.bookcharm.app.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Additional category-specific query methods if needed
}
