package com.shadaev.webify.service;

import com.shadaev.webify.entity.Post;
import com.shadaev.webify.entity.User;
import com.shadaev.webify.repository.PostRepository;
import com.shadaev.webify.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public List<Post> getPostByTitle(String title) {
        if (title != null)
            return postRepository.findByTitle(title);
        return postRepository.findAll();
    }

    public void savePost(Post post, User user) {
        post.setUser(user);
        String longDescription = post.getLongDescription();
        String shortDescription = longDescription.substring(0, Math.min(longDescription.length(), 249));
        post.setShortDescription(shortDescription);
        postRepository.save(post);
    }

    public void deletePost(Post post, User user) {
        user.getPosts().remove(post);
        postRepository.deleteById(post.getId());
        userRepository.save(user);
    }
}
