package com.example.webify.services;

import com.example.webify.models.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    List<Product> products = new ArrayList<>();
    private long ID = 0;

    {
        products.add(new Product(ID++, "200k PC", "ТОП ПК ЗА 200К!!1", 200000, "Moscow", "Tony"));
        products.add(new Product(ID++, "PS 5", "ПК БОЯРЕ ПРОЧЬ!", 60000, "Moscow", "Tony"));
    }

    public List<Product> getProducts() {
        return products;
    }

    public void saveProduct(Product product) {
        product.setId(ID++);
        products.add(product);
    }

    public void deleteProduct(Long id) {
        products.removeIf(product -> product.getId().equals(id));
    }

    public Product getProductById(Long id) {
        for (Product product : products) {
            if (product.getId().equals(id)) return product;
        }
        return null;
    }
}
