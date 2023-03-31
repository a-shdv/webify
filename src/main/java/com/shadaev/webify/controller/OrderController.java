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

    @PostMapping("/user/{user}/cart/order/create")
    public String createOrder(Order order,
                              @AuthenticationPrincipal User userSession, Model model) {
        User userFromDb = userService.findByUsername(userSession.getUsername());
        Cart cart = userFromDb.getCart();
        List<CartItem> cartItemList = cart.getCartItemList();
        List<Product> productList = orderService.toProductList(cartItemList);

        model.addAttribute("products", productList);

        cartService.deleteCartItems(cart);
        order.setProductList(productList);
        orderService.saveOrder(order);

        model.addAttribute("user", userFromDb);
        model.addAttribute("order", order);
        return "order-info";
    }

    @GetMapping("user/{user}/cart/order")
    public String getOrder(@AuthenticationPrincipal User userSession, Model model) {
        User userFromDb = userService.findByUsername(userSession.getUsername());
        Cart cart = userFromDb.getCart();
        List<CartItem> cartItemList = cart.getCartItemList();

        model.addAttribute("user", userFromDb);
        model.addAttribute("cart", cart);
        model.addAttribute("cartItemList", cartItemList);
        return "order";
    }

    @GetMapping("user/{user}/orders")
    public String getUserInfoOrders(@AuthenticationPrincipal User userSession, Model model) {
        User userFromDb = userService.findByUsername(userSession.getUsername());
        List<Order> orderList = userFromDb.getOrderList();

        model.addAttribute("user", userFromDb);
        model.addAttribute("orderList", orderList);
        return "user-info-orders";
    }
}
