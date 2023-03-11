package com.example.webify.services;

import com.example.webify.models.Product;
import com.example.webify.models.User;
import com.example.webify.repositories.ProductRepository;
import com.example.webify.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public List<Product> getProducts(String name) {
        if (name != null)
            return productRepository.findByName(name);
        return productRepository.findAll();
    }

    public void saveProduct(Product product, Principal principal) {
        product.setUser(getUserByPrincipal(principal));
        productRepository.save(product);
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByUsername(principal.getName());
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product getProductById(Long id) {
        return productRepository.getById(id);
    }
}
