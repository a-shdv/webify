package com.shadaev.webify.controller;

import com.shadaev.webify.entity.*;
import com.shadaev.webify.service.CartService;
import com.shadaev.webify.service.OrderService;
import com.shadaev.webify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Controller
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;
    private final UserService userService;

    @Autowired
    public OrderController(OrderService orderService, CartService cartService, UserService userService) {
        this.orderService = orderService;
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping("/user/cart/order")
    public String getOrder(@AuthenticationPrincipal User userSession, Model model) {
        User userFromDb = userService.findUserByUsername(userSession.getUsername());
        Cart cart = userFromDb.getCart();
        List<CartItem> cartItemList = cart.getCartItemList();

        model.addAttribute("user", userFromDb);
        model.addAttribute("cart", cart);
        model.addAttribute("cartItemList", cartItemList);
        return "orders/create";
    }

    @GetMapping("/user/orders")
    public String getUserInfoOrders(@AuthenticationPrincipal User userSession, Model model) {
        User userFromDb = userService.findUserByUsername(userSession.getUsername());
        List<Order> orderList = userFromDb.getOrderList();

        model.addAttribute("user", userFromDb);
        model.addAttribute("orderList", orderList);
        return "users/ordersList";
    }

    @PostMapping("/user/cart/order/create")
    public String createOrder(@AuthenticationPrincipal User userSession, Order order, Model model) {
        User userFromDb = userService.findUserByUsername(userSession.getUsername());
        Cart cart = userFromDb.getCart();
        List<CartItem> cartItemList = cart.getCartItemList();
        List<OrderInfo> orderInfoList = orderService.cartItemListToOrderInfoList(cartItemList, order);

        cartService.deleteCartItemListFromCart(cart);

        order.setOrderInfoList(orderInfoList);
        order.setUser(userFromDb);
        orderService.saveOrder(order);

        orderService.saveOrderInfoList(orderInfoList);

        model.addAttribute("user", userFromDb);
        model.addAttribute("order", order);
        model.addAttribute("orderInfoList", orderInfoList);

        return "orders/show";
    }

    @GetMapping("/user/orders/pdf")
    public ResponseEntity<byte[]> downloadPdf() throws Exception {
        ByteArrayOutputStream outputStream = orderService.generatePdf();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "order.pdf");

        return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
    }
}
