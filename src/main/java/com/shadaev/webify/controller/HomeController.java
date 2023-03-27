package com.shadaev.webify.controller;

import com.shadaev.webify.entity.Post;
import com.shadaev.webify.entity.User;
import com.shadaev.webify.service.PostService;
import com.shadaev.webify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final PostService postService;
    private final UserService userService;

    @GetMapping("/")
    public String home(Model model, Principal principal) {
        model.addAttribute("posts", postService.getPosts());
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model, Principal principal) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "about";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam String filter, Model model, Principal principal) {
        List<Post> posts;

        if (filter != null && !filter.isEmpty()) {
            posts = postService.getPostByHeader(filter.trim());
        } else {
            posts = postService.getPosts();
        }
        model.addAttribute("posts", posts);
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "home";
    }
}
