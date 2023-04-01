package com.shadaev.webify.controller;

import com.shadaev.webify.entity.*;
import com.shadaev.webify.service.CartService;
import com.shadaev.webify.service.OrderService;
import com.shadaev.webify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;
    private final UserService userService;

    @GetMapping("/user/cart/order")
    public String getOrder(@AuthenticationPrincipal User userSession, Model model) {
        User userFromDb = userService.findByUsername(userSession.getUsername());
        Cart cart = userFromDb.getCart();
        List<CartItem> cartItemList = cart.getCartItemList();

        model.addAttribute("user", userFromDb);
        model.addAttribute("cart", cart);
        model.addAttribute("cartItemList", cartItemList);
        return "order";
    }

    @GetMapping("/user/orders")
    public String getUserInfoOrders(@AuthenticationPrincipal User userSession, Model model) {
        User userFromDb = userService.findByUsername(userSession.getUsername());
        List<Order> orderList = userFromDb.getOrderList();
        List<OrderInfo> orderInfoList = orderService.getOrderInfoListFromOrderList(orderList);

        model.addAttribute("user", userFromDb);
        model.addAttribute("orderInfoList", orderInfoList);
        return "user-info-orders";
    }

    @PostMapping("/user/cart/order/create")
    public String createOrder(@AuthenticationPrincipal User userSession, Order order, Model model) {
        User userFromDb = userService.findByUsername(userSession.getUsername());
        Cart cart = userFromDb.getCart();
        List<CartItem> cartItemList = cart.getCartItemList();
        List<OrderInfo> orderInfoList = orderService.cartItemListToOrderInfoList(cartItemList, order);

        model.addAttribute("orderInfoList", orderInfoList);

        cartService.deleteCartItemListFromCart(cart);
        orderService.saveOrder(order);
        orderService.saveOrderInfoList(orderInfoList);

        model.addAttribute("user", userFromDb);
        model.addAttribute("order", order);
        return "order-info";
    }
}
