package com.example.webify.controllers;

import com.example.webify.models.Post;
import com.example.webify.models.User;
import com.example.webify.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final PostService postService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("posts", postService.getPosts());
        return "home";
    }

    @PostMapping("/post/create")
    public String createPost(
            @AuthenticationPrincipal User user,
            @RequestParam String header,
            @RequestParam String description
    ) {
        Post post = new Post(header, description, user);
        postService.savePost(post);
        return "redirect:/";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam String filter, Model model) {
        List<Post> posts;

        if (filter != null && !filter.isEmpty()) {
            posts = postService.getPostByHeader(filter);
        } else {
            posts = postService.getPosts();
        }
        model.addAttribute("posts", posts);
        return "home";
    }
}
