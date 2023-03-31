package com.shadaev.webify.service;

import com.shadaev.webify.entity.CartItem;
import com.shadaev.webify.entity.Order;
import com.shadaev.webify.entity.OrderStatus;
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
        order.setStatus(OrderStatus.IN_PROGRESS);
        orderRepository.save(order);
    }

    public List<Product> toProductList(List<CartItem> cartItemList) {
        List<Product> products = new ArrayList<>();
        Product product;
        for (CartItem cartItem : cartItemList) {
            product = new Product(
                    cartItem.getProduct().getId(),
                    cartItem.getProduct().getName(),
                    cartItem.getProduct().getDescription(),
                    cartItem.getProduct().getPrice(),
                    cartItem.getQuantity(),
                    cartItem.getProduct().getImage(),
                    cartItem.getProduct().getCategory()
            );
            products.add(product);
        }
        return products;
    }

}
