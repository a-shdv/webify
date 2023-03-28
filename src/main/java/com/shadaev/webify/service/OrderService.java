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

    public void saveCartItems(Order order, List<CartItem> cartItems) {
        order.getCartItems().addAll(cartItems);
    }

//    public List<CartItem> getCartItemsFromOrder(Order order) {
//        return order.getCartItems();
//    }

    public Order getOrderById(Long id) {
        return orderRepository.getById(id);
    }

}
