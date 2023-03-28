package com.shadaev.webify.service;

import com.shadaev.webify.entity.CartItem;
import com.shadaev.webify.entity.Order;
import com.shadaev.webify.entity.Product;
import com.shadaev.webify.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }
}
