package com.shadaev.webify.controller;

import com.shadaev.webify.entity.Cart;
import com.shadaev.webify.entity.User;
import com.shadaev.webify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public String login(@AuthenticationPrincipal User userSession, Model model) {
        if (userSession != null) {
            User userFromDb = userService.findByUsername(userSession.getUsername());
            model.addAttribute("user", userFromDb);
        }
        return "login";
    }

    @GetMapping("/registration")
    public String registration(@AuthenticationPrincipal User userSession, Model model) {
        if (userSession != null) {
            User userFromDb = userService.findByUsername(userSession.getUsername());
            model.addAttribute("user", userFromDb);
        }
        return "registration";
    }

    @GetMapping("/user")
    public String getUserInfo(@AuthenticationPrincipal User userSession, Model model) {
        User userFromDb = userService.findByUsername(userSession.getUsername());

        model.addAttribute("user", userFromDb);
        return "user-info";
    }

    @PostMapping("/registration")
    public String createUser(@AuthenticationPrincipal User userSession, Model model) {
        User userFromDb = userService.findByUsername(userSession.getUsername());

        if (userFromDb != null) {
            model.addAttribute("errorMessage", "Пользователь с именем " + userFromDb.getUsername() + " уже существует!");
            return "registration";
        } else {
            userService.saveUser(userFromDb);
            userFromDb.setCart(new Cart());
        }

        model.addAttribute("user", userFromDb);
        return "redirect:/login";
    }
}
