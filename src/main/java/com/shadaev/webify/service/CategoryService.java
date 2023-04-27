package com.shadaev.webify.service;

import com.shadaev.webify.entity.Category;
import com.shadaev.webify.entity.Product;
import com.shadaev.webify.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Product> findCategoryById(Long categoryId) {
        return categoryRepository.getById(categoryId).getProductList();
    }
}
