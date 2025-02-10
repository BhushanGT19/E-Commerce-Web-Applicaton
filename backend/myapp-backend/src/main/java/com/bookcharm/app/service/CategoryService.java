package com.bookcharm.app.service;

import com.bookcharm.app.model.Category;

import java.util.List;

public interface CategoryService {
    Category getCategoryById(Long categoryId);
    Category createCategory(Category category);
    Category updateCategory(Long categoryId, Category category);
    boolean deleteCategory(Long categoryId);
    List<Category> getAllCategories();
    // Other CategoryService methods
}
