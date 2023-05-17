package com.shadaev.webify.controller;

import com.shadaev.webify.entity.*;
import com.shadaev.webify.service.OrderService;
import com.shadaev.webify.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.ByteArrayOutputStream;
import java.util.Comparator;
import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public UserController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal User userSession, Model model) {
        User userFromDb = userService.getUser(userSession);
        model.addAttribute("user", userFromDb);
        return "/users/profile";
    }

    @GetMapping("/login")
    public String login() {
        return "users/login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "users/registration";
    }

    @GetMapping("/user/orders")
    public String getOrders(@AuthenticationPrincipal User userSession, Model model) {
        User userFromDb = userService.findUserByUsername(userSession.getUsername());
        List<Order> orders = userFromDb.getOrders();
        orders.sort(Comparator.comparing(Order::getCreatedAt).reversed());

        model.addAttribute("user", userFromDb);
        model.addAttribute("orders", orders);

        return "users/ordersList";
    }

    @GetMapping("/orders")
    public String getAllOrders(@AuthenticationPrincipal User userSession, Model model) {
        User userFromDb = userService.findUserByUsername(userSession.getUsername());
        List<Order> orders = userService.getAllOrders();
        orders.sort(Comparator.comparing(Order::getCreatedAt).reversed());

        model.addAttribute("user", userFromDb);
        model.addAttribute("orders", orders);

        return "users/adminOrders";
    }

    @PostMapping("/orders/{order}")
    public String changeOrderStatus(@PathVariable("order") Order order, Model model) {
        orderService.changeOrderStatus(order);

        model.addAttribute("order", order);

        return "redirect:/orders";
    }

    @GetMapping("/user/posts")
    public String getPosts(@AuthenticationPrincipal User userSession, Model model) {
        User userFromDb = userService.findUserByUsername(userSession.getUsername());
        List<Post> posts = userFromDb.getPosts();
        posts.sort(Comparator.comparing(Post::getCreatedAt).reversed());

        model.addAttribute("user", userFromDb);
        model.addAttribute("posts", posts);

        return "users/postsList";
    }

    @GetMapping("/user/{id}")
    public String getUserById(@PathVariable(value = "id") Long id,
                              @AuthenticationPrincipal User userSession, Model model) {
        User currentUser = userService.getUser(userSession);
        User anotherUser = userService.getUserById(id);

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("anotherUser", anotherUser);

        return "/users/user";
    }

    @PostMapping("/registration")
    public String createUser(User user, Model model) {
        User userFromDb = (User) userService.loadUserByUsername(user.getUsername());
        if (userFromDb != null) {
            model.addAttribute("errorMessage", "Пользователь с именем " + user.getUsername() + " уже существует!");
            return "users/registration";
        }

        user.setCart(new Cart());
        userService.createUser(user);

        model.addAttribute("user", user);
        return "redirect:/login";
    }

    @GetMapping("/user/orders/pdf")
    public ResponseEntity<byte[]> downloadPdf() throws Exception {
        ByteArrayOutputStream outputStream = userService.generatePdf();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "order.pdf");

        return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
    }
}
