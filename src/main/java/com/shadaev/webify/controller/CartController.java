package com.shadaev.webify.controller;

import com.shadaev.webify.entity.Cart;
import com.shadaev.webify.entity.Product;
import com.shadaev.webify.entity.User;
import com.shadaev.webify.service.CartService;
import com.shadaev.webify.service.ProductService;
import com.shadaev.webify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final ProductService productService;
    private final UserService userService;

    @GetMapping("/user/{user}/cart")
    public String cart(Model model, Principal principal) {
        User user = userService.getUserByPrincipal(principal);

        model.addAttribute("user", user);
        model.addAttribute("cart", user.getCart());
        model.addAttribute("items", user.getCart().getCartItems());

        return "cart";
    }

    @PostMapping("/user/{user}/cart/{cart}/add/{product}")
    public String addItemToCart(Product product,
                                @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
                                Principal principal) {
        cartService.addItemToCart(product, quantity, userService.getUserByPrincipal(principal));
        return "redirect:/categories";
    }


    @PostMapping(value = "/user/{user}/cart/{cart}/update/{product}", params = "action=update")
    public String updateCart(@PathVariable(value = "product") Long productId,
                             @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
                             Model model, Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        Product product = productService.getProductById(productId);
        Cart cart = cartService.updateItemInCart(product, quantity, user);
        model.addAttribute("cart", cart);
        return "redirect:/user/{user}/cart";
    }

    @PostMapping(value = "/user/{user}/cart/{cart}/update/{product}", params = "action=delete")
    public String deleteItemFromCart(@PathVariable(value = "product") Long productId,
                                     Model model, Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        Product product = productService.getProductById(productId);
        Cart cart = cartService.deleteItemFromCart(product, user);
        model.addAttribute("cart", cart);
        return "redirect:/user/{user}/cart";
    }
}