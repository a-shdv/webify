package com.shadaev.webify.service;

import com.shadaev.webify.entity.Order;
import com.shadaev.webify.entity.Product;
import com.shadaev.webify.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    public Order getOrderById(Long id) {
        return orderRepository.getById(id);
    }

}
