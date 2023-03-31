package com.shadaev.webify.controller;

import com.shadaev.webify.entity.Cart;
import com.shadaev.webify.entity.CartItem;
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

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final ProductService productService;
    private final UserService userService;

    @GetMapping("/user/cart")
    public String getCart(@AuthenticationPrincipal User userSession, Model model) {
        User userFromDb = userService.findByUsername(userSession.getUsername());
        Cart cart = userFromDb.getCart();
        List<CartItem> cartItemList = cart.getCartItemList();

        model.addAttribute("user", userFromDb);
        model.addAttribute("cart", cart);
        model.addAttribute("cartItemList", cartItemList);
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
    public String updateCart(@PathVariable(value = "product") Long productId,
                             @RequestParam(value = "quantity", required = false, defaultValue = "1") Integer quantity,
                             @AuthenticationPrincipal User userSession, Model model) {
        User userFromDb = userService.findByUsername(userSession.getUsername());
        Product product = productService.findProductById(productId);
        Cart cart = cartService.updateCartItemInCart(product, quantity, userFromDb);

        model.addAttribute("cart", cart);
        return "redirect:/user/cart";
    }

    @PostMapping(value = "/user/cart/update/{product}", params = "action=delete")
    public String deleteItemFromCart(@PathVariable(value = "product") Long productId,
                                     @AuthenticationPrincipal User userSession, Model model) {
        User userFromDb = userService.findByUsername(userSession.getUsername());
        Product product = productService.findProductById(productId);
        Cart cart = cartService.deleteCartItemFromCart(product, userFromDb);

        model.addAttribute("cart", cart);
        return "redirect:/user/cart";
    }
}