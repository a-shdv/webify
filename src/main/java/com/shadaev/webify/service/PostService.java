package com.shadaev.webify.service;

import com.shadaev.webify.entity.Post;
import com.shadaev.webify.entity.User;
import com.shadaev.webify.repository.PostRepository;
import lombok.RequiredArgsConstructor;
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

    public void savePost(Post post, User user) {
        post.setUser(user);
        postRepository.save(post);
    }

    public List<Post> findPostByHeader(String header) {
        if (header != null)
            return postRepository.findByHeader(header);
        return postRepository.findAll();
    }

    public List<Post> findPostList() {
        return postRepository.findAll();
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
