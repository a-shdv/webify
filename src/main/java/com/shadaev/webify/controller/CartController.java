package com.shadaev.webify.controller;

import com.shadaev.webify.entity.Cart;
import com.shadaev.webify.entity.CartProduct;
import com.shadaev.webify.entity.Product;
import com.shadaev.webify.entity.User;
import com.shadaev.webify.service.CartService;
import com.shadaev.webify.service.ProductService;
import com.shadaev.webify.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final ProductService productService;
    private final UserService userService;

    @Autowired
    public CartController(CartService cartService,
                          ProductService productService,
                          UserService userService) {
        this.cartService = cartService;
        this.productService = productService;
        this.userService = userService;
    }


    @GetMapping
    public String getCart(@AuthenticationPrincipal User userSession, Model model) {
        User userFromDb = userService.findUserByUsername(userSession.getUsername());
        Cart cart = userFromDb.getCart();

        model.addAttribute("user", userFromDb);
        model.addAttribute("cart", cartService.getCartById(cart.getId()));
        model.addAttribute("cartProducts", cartService.getCartById(cart.getId()).getCartProducts());
        return "carts/cart";
    }

    @PostMapping("/{product}")
    public String addProduct(@PathVariable(value = "product") Product product,
                             @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
                             @AuthenticationPrincipal User userSession) {
        User userFromDb = userService.findUserByUsername(userSession.getUsername());
        Cart cart = userFromDb.getCart();

        cartService.createProduct(cart, product, quantity, product.getPrice() * quantity);

        return "redirect:/categories/" + product.getCategory().getId();
    }


//    @PostMapping(value = "/user/cart/update/{product}", params = "action=update")
//    public String updateCartItemInCart(@PathVariable(value = "product") Long productId,
//                                       @RequestParam(value = "quantity", required = false, defaultValue = "1") Integer quantity,
//                                       @AuthenticationPrincipal User userSession, Model model) {
//        User userFromDb = userService.findUserByUsername(userSession.getUsername());
//        Product product = productService.findProductById(productId);
//        Cart updatedCart = cartService.updateCartItemInCart(product, quantity, userFromDb.getCart());
//
//        model.addAttribute("cart", updatedCart);
//        return "redirect:/user/cart";
//    }

//    @PostMapping(value = "/user/cart/update/{product}", params = "action=delete")
//    public String deleteCartItemFromCart(@PathVariable(value = "product") Long productId,
//                                         @AuthenticationPrincipal User userSession, Model model) {
//        User userFromDb = userService.findUserByUsername(userSession.getUsername());
//        Product product = productService.findProductById(productId);
//        Cart cartWithDeletedItem = cartService.deleteCartItemFromCart(product, userFromDb.getCart());
//
//        model.addAttribute("cart", cartWithDeletedItem);
//        return "redirect:/user/cart";
//    }
}