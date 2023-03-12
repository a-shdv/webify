package com.example.webify.repositories;

import com.example.webify.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Сам создаст метод, который будет искать по названию (name)
    // Spring сам поймет, что нужно искать, исходя из названия метода
    List<Product> findByName(String name);
}
