package com.shadaev.webify.controller;

import com.shadaev.webify.entity.Post;
import com.shadaev.webify.entity.User;
import com.shadaev.webify.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/user/{user}/posts")
    public String userInfoPosts(@PathVariable("user") User user, Model model) {
        model.addAttribute("user", user);
        return "user-info-posts";
    }

    @PostMapping("/user/{user}/posts/post/create")
    public String createPost(Post post, Principal principal) {
        postService.savePost(post, principal);
        return "redirect:/user/{user}/posts";
    }
}
