package com.shadaev.webify.controller;

import com.shadaev.webify.entity.*;
import com.shadaev.webify.service.CartService;
import com.shadaev.webify.service.OrderService;
import com.shadaev.webify.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;
    private final UserService userService;

    @Autowired
    public OrderController(OrderService orderService,
                           CartService cartService,
                           UserService userService) {
        this.orderService = orderService;
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping("/create")
    public String createOrderForm(@AuthenticationPrincipal User userSession, Model model) {
        User userFromDb = userService.findUserByUsername(userSession.getUsername());
        Cart cart = cartService.getCartById(userFromDb.getCart().getId());
        List<CartProduct> cartProducts = cart.getCartProducts();

        model.addAttribute("user", userFromDb);
        model.addAttribute("cart", cart);
        model.addAttribute("cartProducts", cartProducts);

        return "orders/create";
    }

    @PostMapping("/create")
    public String createOrder(@RequestBody MultiValueMap<String, String> orderData,
            @AuthenticationPrincipal User userSession, Model model) {
        User userFromDb = userService.findUserByUsername(userSession.getUsername());
        Cart cart =userFromDb.getCart();
        Order order = orderService.createOrder(orderData, cart);

        model.addAttribute("user", userFromDb);
        model.addAttribute("order", order);

        cartService.deleteCartProducts(cart);
        return "orders/show";
    }
//
//    @GetMapping("/user/orders")
//    public String getUserInfoOrders(@AuthenticationPrincipal User userSession, Model model) {
//        User userFromDb = userService.findUserByUsername(userSession.getUsername());
//        List<Order> orderList = userFromDb.getOrderList();
//
//        model.addAttribute("user", userFromDb);
//        model.addAttribute("orderList", orderList);
//        return "users/ordersList";
//    }
//
}
