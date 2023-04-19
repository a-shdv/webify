package com.shadaev.webify.controller;

import com.shadaev.webify.entity.Cart;
import com.shadaev.webify.entity.User;
import com.shadaev.webify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal User userSession, Model model) {
        User userFromDb = userService.findUser(userSession);
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

    @GetMapping("/user/{id}")
    public String findUserById(@PathVariable(value = "id") Long id,
                               @AuthenticationPrincipal User userSession, Model model) {
        User currentUser = userService.findUser(userSession);
        User anotherUser = userService.findUserById(id);

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("anotherUser", anotherUser);
        return "another-user-info";
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


}
