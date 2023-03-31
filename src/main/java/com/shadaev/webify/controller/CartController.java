package com.shadaev.webify.controller;

import com.shadaev.webify.entity.Cart;
import com.shadaev.webify.entity.Product;
import com.shadaev.webify.entity.User;
import com.shadaev.webify.service.CartService;
import com.shadaev.webify.service.ProductService;
import com.shadaev.webify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final ProductService productService;
    private final UserService userService;

    @GetMapping("/user/cart")
    public String getCart(@AuthenticationPrincipal User userSession, Model model) {
        User userFromDb = userService.findByUsername(userSession.getUsername());

        model.addAttribute("user", userFromDb);
        model.addAttribute("cart", userFromDb.getCart());
        model.addAttribute("cartItemList", userFromDb.getCart().getCartItemList());
        return "cart";
    }

    @PostMapping("/user/cart/add/{product}")
    public String createItemInCart(@PathVariable(value = "product") Long productId,
                                   @RequestParam(value = "quantity", required = false, defaultValue = "1") Integer quantity,
                                   @AuthenticationPrincipal User userSession) {
        User userFromDb = userService.findByUsername(userSession.getUsername());
        Product product = productService.findProductById(productId);

        cartService.saveCartItemToCart(product, quantity, userFromDb);

        return "redirect:/categories";
    }


    @PostMapping(value = "/user/cart/update/{product}", params = "action=update")
    public String updateCartItemInCart(@PathVariable(value = "product") Long productId,
                                       @RequestParam(value = "quantity", required = false, defaultValue = "1") Integer quantity,
                                       @AuthenticationPrincipal User userSession, Model model) {
        User userFromDb = userService.findByUsername(userSession.getUsername());
        Product product = productService.findProductById(productId);
        Cart updatedCart = cartService.updateCartItemInCart(product, quantity, userFromDb.getCart());

        model.addAttribute("cart", updatedCart);
        return "redirect:/user/cart";
    }

    @PostMapping(value = "/user/cart/update/{product}", params = "action=delete")
    public String deleteCartItemFromCart(@PathVariable(value = "product") Long productId,
                                         @AuthenticationPrincipal User userSession, Model model) {
        User userFromDb = userService.findByUsername(userSession.getUsername());
        Product product = productService.findProductById(productId);
        Cart cartWithDeletedItem = cartService.deleteCartItemFromCart(product, userFromDb.getCart());

        model.addAttribute("cart", cartWithDeletedItem);
        return "redirect:/user/cart";
    }
}