package com.shadaev.webify.controller;

import com.shadaev.webify.entity.Product;
import com.shadaev.webify.repository.UserRepository;
import com.shadaev.webify.service.CartService;
import com.shadaev.webify.service.ProductService;
import com.shadaev.webify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final ProductService productService;
    private final UserService userService;

    @GetMapping("/user/{user}/cart")
    public String cart(Model model, Principal principal) {
        model.addAttribute("products", cartService.getProducts());
        model.addAttribute("total", cartService.getTotalAmount());
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "cart";
    }

    @GetMapping("/user/{user}/cart/add/{id}")
    public String add(@PathVariable("id") Long id) {
        Product product = productService.getProductById(id);
        if (product != null)
            cartService.add(product);

        return "redirect:/products/{id}";
    }

    @GetMapping("/user/{user}/cart/remove/{id}")
    public String remove(@PathVariable("id") Long id) {
        Product product = productService.getProductById(id);
        if (product != null)
            cartService.remove(product);

        return "redirect:/user/{user}/cart";
    }

//    @GetMapping("/checkout")
//    public String checkout() {
//
//        cartService.checkout();
//
//        return "cart";
//    }
}