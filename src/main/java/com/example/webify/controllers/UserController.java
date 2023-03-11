package com.example.webify.controllers;

import com.example.webify.models.User;
import com.example.webify.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @GetMapping("/user/{user}")
    public String userInfo(@PathVariable("user") User user, Model model) {
        model.addAttribute("user", user);
        return "user-info";
    }

    @GetMapping("/user/{user}/posts")
    public String userInfoPosts(@PathVariable("user") User user, Model model) {
        model.addAttribute("user", user);
        return "user-info-posts";
    }

    @GetMapping("/user/{user}/products")
    public String userInfoProducts(@PathVariable("user") User user, Model model) {
        model.addAttribute("user", user);
        return "user-info-products";
    }

    @PostMapping("/registration")
    public String createUser(User user, Model model) {
        User userFromDb = (User) userService.loadUserByUsername(user.getUsername());
        if (userFromDb != null) {
            model.addAttribute("errorMessage", "Пользователь с именем " + user.getUsername() + " уже существует!");
            return "registration";
        }
        userService.saveUser(user);
        return "redirect:/login";
    }
}
