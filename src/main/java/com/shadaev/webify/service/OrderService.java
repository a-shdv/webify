package com.shadaev.webify.service;

import com.shadaev.webify.entity.CartItem;
import com.shadaev.webify.entity.Order;
import com.shadaev.webify.entity.Product;
import com.shadaev.webify.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    public List<Product> toProductsList(List<CartItem> cartItems) {
        List<Product> products = new ArrayList<>();
        Product product;
        for (CartItem cartItem : cartItems) {
            product = new Product(
                    cartItem.getProduct().getName(),
                    cartItem.getProduct().getDescription(),
                    cartItem.getProduct().getPrice(),
                    cartItem.getProduct().getImage(),
                    cartItem.getProduct().getCategory()
            );
            products.add(product);
        }
        return products;
    }

}
