package com.shadaev.webify;

import com.shadaev.webify.post.Post;
import com.shadaev.webify.post.PostService;
import com.shadaev.webify.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final PostService postService;
    private final UserService userService;

    @GetMapping("/")
    public String index(Model model, Principal principal) {
        model.addAttribute("posts", postService.getPosts());
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "index";
    }

    @GetMapping("about")
    public String about(Model model, Principal principal) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "about";
    }

    @PostMapping("/user/{user}/posts/post/create")
    public String createPost(Post post, Principal principal) {
        postService.savePost(post, principal);
        return "redirect:/user/{user}/posts";
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
        return "index";
    }
}
