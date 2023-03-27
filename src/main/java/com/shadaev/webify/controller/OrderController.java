package com.shadaev.webify.controller;

import com.shadaev.webify.entity.Cart;
import com.shadaev.webify.entity.User;
import com.shadaev.webify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final UserService userService;

    @GetMapping("user/{user}/cart/place_order")
    public String placeOrder(Model model, Principal principal) {
        User user = userService.getUserByPrincipal(principal);
//        if (user.getCity().trim().isEmpty() || user.getShippingAddress().trim().isEmpty()
//                || user.getPhoneNumber().trim().isEmpty()) {
//            model.addAttribute("user", user);
//            model.addAttribute("error", "Все поля должны быть заполнены!");
//            return "account";
//        } else {
            Cart cart = user.getCart();
            model.addAttribute("user", user);
            model.addAttribute("cart", cart);
//        }
        return "order-place";
    }


//    @GetMapping("/order")
//    public String order() {
//        return "order";
//    }

}
