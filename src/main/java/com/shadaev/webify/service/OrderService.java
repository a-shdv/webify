package com.shadaev.webify.service;

import com.shadaev.webify.entity.*;
import com.shadaev.webify.repository.OrderInfoRepository;
import com.shadaev.webify.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderInfoRepository orderInfoRepository;

    public void saveOrder(Order order) {
        order.setStatus(OrderStatus.IN_PROGRESS);
        orderRepository.save(order);
    }

    public void saveOrderInfoList(List<OrderInfo> orderInfoList) {
        orderInfoRepository.saveAll(orderInfoList);
    }

    public List<OrderInfo> getOrderInfoListFromOrderList(List<Order> orderList) {
        List<OrderInfo> orderInfoList = new ArrayList<>();

        for (Order order : orderList) {
            orderInfoList.add(order.getOrderInfo());
        }
        return orderInfoList;
    }

    public List<OrderInfo> cartItemListToOrderInfoList(List<CartItem> cartItemList, Order order) {
        List<OrderInfo> orderInfoList  = new ArrayList<>();
        OrderInfo orderInfo;
        for (CartItem cartItem : cartItemList) {
            orderInfo = new OrderInfo(
                    cartItem.getTotalPrice(),
                    cartItem.getQuantity(),
                    cartItem.getProduct(),
                    order
                    );
            orderInfoList.add(orderInfo);
        }
        return orderInfoList;
    }

}
