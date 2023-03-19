package com.shadaev.webify.service;

import com.shadaev.webify.entity.Product;
import com.shadaev.webify.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CartService {
    private final Map<Product, Integer> products;

    public void add(Product product) {
        products.computeIfPresent(
                product, (key, value) -> value + 1
        );

        products.putIfAbsent(product, 1);
    }

    public void remove(Product product) {
        var quantity = products.get(product);

        if (quantity > 1) {
            products.replace(product, quantity - 1);
        } else if (quantity == 1) {
            products.remove(product);
        }
    }

//    public void checkout() {
//        products.forEach((product, cartQuantity) -> productRepository.findById(product.getId())
//                .map(Product::getQuantity)
//                .ifPresent(quantity -> product.setQuantity(quantity - cartQuantity))
//        );
//
//        productRepository.saveAll(
//                products.keySet()
//        );
//
//        products.clear();
//    }

    public BigDecimal getTotalAmount() {
        return products.entrySet().stream()
                .map(x -> x.getKey().getPrice().multiply(BigDecimal.valueOf(x.getValue())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public Map<Product, Integer> getProducts() {
        return Map.copyOf(products);
    }
}
