package com.shadaev.webify.service;

import com.shadaev.webify.entity.Post;
import com.shadaev.webify.entity.Product;
import com.shadaev.webify.repository.ProductRepository;
import com.shadaev.webify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product getProductById(Long id) {
        return productRepository.getById(id);
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductByName(String name) {
        if (name != null)
            return productRepository.findByName(name);
        return productRepository.findAll();
    }
}
