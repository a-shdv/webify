package com.shadaev.webify.category;

import com.shadaev.webify.product.Product;
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

    public List<Product> getProductsByCategoryId(Long id) {
        return categoryRepository.getById(id).getProducts();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.getById(id);
    }
}
