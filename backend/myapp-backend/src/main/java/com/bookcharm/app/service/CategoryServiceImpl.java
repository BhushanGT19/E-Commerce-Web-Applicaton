package com.bookcharm.app.service;

import com.bookcharm.app.model.Category;
import com.bookcharm.app.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        return optionalCategory.orElse(null);
    }

    @Override
    public Category createCategory(Category category) {
        // Add logic for category creation, validation, etc.
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long categoryId, Category updatedCategory) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isPresent()) {
            Category existingCategory = optionalCategory.get();
            // Update the existing category with the new information
            existingCategory.setCategoryType(updatedCategory.getCategoryType());

            // Save and return the updated category
            return categoryRepository.save(existingCategory);
        }
        return null;
    }

    @Override
    public boolean deleteCategory(Long categoryId) {
        if (categoryRepository.existsById(categoryId)) {
            categoryRepository.deleteById(categoryId);
            return true;
        }
        return false;
    }

    // Add other CategoryService methods if needed
}
