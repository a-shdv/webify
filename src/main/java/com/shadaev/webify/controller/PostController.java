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
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @GetMapping("/user/posts")
    public String getUserInfoPosts(@AuthenticationPrincipal User userSession, Model model) {
        User userFromDb = userService.findUserByUsername(userSession.getUsername());
        List<Post> postList = postService.findPostList();

        model.addAttribute("user", userFromDb);
        model.addAttribute("postList", postList);
        return "users/postsList";
    }

    @PostMapping("/user/posts/post/create")
    public String createPost(Post post, @AuthenticationPrincipal User userSession) {
        User userFromDb = userService.findUserByUsername(userSession.getUsername());

        postService.savePost(post, userFromDb);

        return "redirect:/user/posts";
    }
}
