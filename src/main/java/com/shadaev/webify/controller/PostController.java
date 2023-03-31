package com.shadaev.webify.controller;

import com.shadaev.webify.entity.Post;
import com.shadaev.webify.entity.User;
import com.shadaev.webify.service.PostService;
import com.shadaev.webify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @PostMapping("/user/{user}/posts/post/create")
    public String createPost(Post post, @AuthenticationPrincipal User userSession) {
        User userFromDb = userService.findByUsername(userSession.getUsername());

        postService.savePost(post, userFromDb);

        return "redirect:/user/{user}/posts";
    }

    @GetMapping("/user/{user}/posts")
    public String getUserInfoPosts(@PathVariable("user") User user, Model model) {
        List<Post> postList = postService.findPostList();

        model.addAttribute("user", user);
        model.addAttribute("postList", postList);
        return "user-info-posts";
    }
}
