package com.shadaev.webify.service;

import com.shadaev.webify.entity.Post;
import com.shadaev.webify.entity.User;
import com.shadaev.webify.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
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
        postRepository.save(post);
    }


    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
