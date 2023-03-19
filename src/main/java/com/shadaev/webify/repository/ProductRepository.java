package com.shadaev.webify.repository;

import com.shadaev.webify.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Сам создаст метод, который будет искать по названию (name)
    // Spring сам поймет, что нужно искать, исходя из названия метода
    List<Product> findByName(String name);
}
