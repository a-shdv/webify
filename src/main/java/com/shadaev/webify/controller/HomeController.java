package com.shadaev.webify.controller;

import com.shadaev.webify.entity.Post;
import com.shadaev.webify.entity.User;
import com.shadaev.webify.service.PostService;
import com.shadaev.webify.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;

@Controller
public class HomeController {
    private final PostService postService;
    private final UserService userService;

    @Autowired
    public HomeController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping(value = {"/", "/home", "/posts"})
    public String home(Model model) {
        List<Post> posts = postService.getAllPosts();
        posts.sort(Comparator.comparing(Post::getCreatedAt).reversed());
        model.addAttribute("posts", posts);
        return "home";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam String filter,
                         @AuthenticationPrincipal User userSession, Model model) {
        if (userSession != null) {
            User userFromDb = userService.findUserByUsername(userSession.getUsername());
            model.addAttribute("user", userFromDb);
        }
        List<Post> postList;

        if (filter != null && !filter.isEmpty()) {
            postList = postService.getPostByTitle(filter.trim());
        } else {
            postList = postService.getAllPosts();
        }

        model.addAttribute("postList", postList);
        return "home";
    }
}
