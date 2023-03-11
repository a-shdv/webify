package com.example.webify.services;

import com.example.webify.models.Post;
import com.example.webify.models.User;
import com.example.webify.repositories.PostRepository;
import com.example.webify.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    public List<Post> getPostByHeader(String header) {
        if (header != null)
            return postRepository.findByHeader(header);
        return postRepository.findAll();
    }

    public void savePost(Post post, Principal principal) {
        post.setUser(getUserByPrincipal(principal));
        postRepository.save(post);
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByUsername(principal.getName());
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public Post getPostById(Long id) {
        return postRepository.getById(id);
    }
}
