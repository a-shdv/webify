package com.shadaev.webify.controller;

import com.shadaev.webify.entity.*;
import com.shadaev.webify.service.CartService;
import com.shadaev.webify.service.OrderService;
import com.shadaev.webify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final CartService cartService;

    @GetMapping("user/{user}/cart/order")
    public String order(Model model, Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        Cart cart = user.getCart();

        model.addAttribute("user", user);
        model.addAttribute("cart", cart);
        model.addAttribute("items", cart.getCartItems());

        return "order";
    }

    @PostMapping("/user/{user}/cart/order/create")
    public String createOrder(Order order, Model model, Principal principal) {
        User user = userService.getUserByPrincipal(principal);

        model.addAttribute("products", orderService.toProductsList(user.getCart().getCartItems()));

        cartService.deleteCartItems(user.getCart());

        order.setStatus(OrderStatus.IN_PROGRESS);
        orderService.saveOrder(order);

        model.addAttribute("user", user);
        model.addAttribute("order", order);
        return "order-info";
    }

    @GetMapping("user/{user}/orders")
    public String userInfoOrders(Model model, Principal principal) {
        User user = userService.getUserByPrincipal(principal);

        model.addAttribute("user", user);
        model.addAttribute("orders", user.getOrders());

        return "user-info-orders";
    }
}
