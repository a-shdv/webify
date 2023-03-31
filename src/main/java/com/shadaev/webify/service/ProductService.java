package com.shadaev.webify.service;

import com.shadaev.webify.entity.Product;
import com.shadaev.webify.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public Product findProductById(Long id) {
        return productRepository.getById(id);
    }

    public List<Product> findProductList() {
        return productRepository.findAll();
    }

    public List<Product> findProductListByName(String name) {
        if (name != null)
            return productRepository.findByName(name);
        return productRepository.findAll();
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
