package com.example.webify.services;

import com.example.webify.models.Category;
import com.example.webify.models.Product;
import com.example.webify.repositories.CategoryRepository;
import com.example.webify.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public List<Product> getProductListByCategoryId(Long id) {
        return categoryRepository.getById(id).getProductList();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.getById(id);
    }
}
