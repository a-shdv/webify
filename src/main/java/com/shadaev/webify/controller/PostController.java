package com.shadaev.webify.controller;

import com.shadaev.webify.entity.Post;
import com.shadaev.webify.entity.Product;
import com.shadaev.webify.entity.User;
import com.shadaev.webify.service.PostService;
import com.shadaev.webify.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public PostController(PostService postService,
                          UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("/{post}")
    public String getPostById(@PathVariable(value = "post") Post post, Model model) {
        model.addAttribute("post", post);

        return "posts/show";
    }

    @PostMapping("/create")
    public String createPost(Post post,
                             @AuthenticationPrincipal User userSession,
                             @RequestParam("file") MultipartFile file) throws IOException {
        User userFromDb = userService.findUserByUsername(userSession.getUsername());
        if (!file.isEmpty() && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            post.setFilename(resultFilename);
        }

        postService.savePost(post, userFromDb);

        return "redirect:/";
    }

    @PutMapping("/edit/{post}")
    public String editPost(@PathVariable(value = "post") Post post,
                           @AuthenticationPrincipal User user, Model model) {


        return "";
    }
}
