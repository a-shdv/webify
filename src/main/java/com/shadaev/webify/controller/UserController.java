package com.shadaev.webify.controller;

import com.shadaev.webify.entity.Cart;
import com.shadaev.webify.entity.User;
import com.shadaev.webify.service.UserService;
import lombok.RequiredArgsConstructor;
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
        if (userSession != null) {
            User userFromDb = userService.findByUsername(userSession.getUsername());
            model.addAttribute("user", userFromDb);
        }
        return "user-info";
    }

    @GetMapping("/user/{user}")
    public String getAnotherUserInfo(@PathVariable(value = "user") Long userId,
                                     @AuthenticationPrincipal User userSession, Model model) {
        if (userSession != null) {
            User userFromDb = userService.findByUsername(userSession.getUsername());
            model.addAttribute("user", userFromDb);
        }
        User anotherUser = userService.findUserById(userId);

        model.addAttribute("anotherUser", anotherUser);
        return "another-user-info";
    }

    @PostMapping("/registration")
    public String createUser(User user, Model model) {
        User userFromDb = (User) userService.loadUserByUsername(user.getUsername());

        if (userFromDb != null) {
            model.addAttribute("errorMessage", "Пользователь с именем " + user.getUsername() + " уже существует!");
            return "registration";
        }

        user.setCart(new Cart());
        userService.saveUser(user);

        model.addAttribute("user", user);
        return "redirect:/login";
    }
}
