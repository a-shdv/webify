package com.shadaev.webify.service;

import com.shadaev.webify.entity.*;
import com.shadaev.webify.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(MultiValueMap<String, String> orderData, Cart cart) {
        Order order = parseOrderData(orderData);
        order.setUser(cart.getUser());
        addProductsToOrder(order, cart.getCartProducts());
        orderRepository.save(order);
        return order;
    }

    public void changeOrderStatus(Order order) {
        OrderStatus[] statuses = OrderStatus.values();
        for (OrderStatus status : statuses) {
            if (status.getValue() <= order.getStatus().getValue()) {
                continue;
            }
            order.setStatus(status);
            break;
        }
        orderRepository.save(order);
    }

    private Order parseOrderData(MultiValueMap<String, String> orderData) {
        return new Order(
                orderData.getFirst("shippingAddress"),
                orderData.getFirst("comment"),
                Integer.parseInt(Objects.requireNonNull(orderData.getFirst("entranceNumber"))),
                Integer.parseInt(Objects.requireNonNull(orderData.getFirst("doorPassword"))),
                Integer.parseInt(Objects.requireNonNull(orderData.getFirst("floor"))),
                Integer.parseInt(Objects.requireNonNull(orderData.getFirst("apartmentNumber"))));
    }

    private void addProductsToOrder(Order order, List<CartProduct> cartProducts) {
        int quantity;
        double price;
        List<OrderProduct> orderProducts = new ArrayList<>();

        for (CartProduct cp : cartProducts) {
            quantity = cp.getQuantity();
            price = cp.getPrice() * quantity;
            orderProducts.add(new OrderProduct(order, cp.getProduct(), quantity, price));
        }
        order.setOrderProducts(orderProducts);
        order.setStatus(OrderStatus.PROCESSING);
    }

}
