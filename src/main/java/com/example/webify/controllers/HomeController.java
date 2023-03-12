package com.example.webify.controllers;

import com.example.webify.models.Post;
import com.example.webify.models.User;
import com.example.webify.services.PostService;
import com.example.webify.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping("/post/create")
    public String createPost(Post post, Principal principal) {
        postService.savePost(post, principal);
        return "redirect:/";
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
