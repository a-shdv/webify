package com.shadaev.webify.controller;

import com.shadaev.webify.entity.Post;
import com.shadaev.webify.entity.User;
import com.shadaev.webify.service.PostService;
import com.shadaev.webify.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@Controller
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @Autowired
    public PostController(PostService postService,
                          UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("/{post}")
    public String getById(@PathVariable(value = "post") Post post, Model model) {
        model.addAttribute("post", post);

        return "posts/show";
    }

    @PostMapping("/create")
    public String create(Post post,
                         @AuthenticationPrincipal User userSession,
                         @RequestParam(value = "file",required = false) MultipartFile file) throws IOException {
        User userFromDb = userService.findUserByUsername(userSession.getUsername());
        postService.uploadImage(post, file);
        postService.savePost(post, userFromDb);
        return "redirect:/";
    }

    @GetMapping("/edit/{post}")
    public String editForm(@PathVariable(value = "post") Post post, Model model) {
        model.addAttribute("post", post);
        return "posts/edit";
    }

    @PostMapping("/edit/{post}")
    public String edit(@PathVariable("post") Post post,
                       @RequestParam("title") String title,
                       @RequestParam("description") String description,
                       @RequestParam(value = "file",required = false) MultipartFile file,
                       Model model) throws IOException {
        postService.editPost(post, title, description, file);
        model.addAttribute("post", post);
        return "redirect:/";
    }
}
