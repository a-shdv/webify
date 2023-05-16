package com.shadaev.webify.service;

import com.shadaev.webify.entity.Post;
import com.shadaev.webify.entity.User;
import com.shadaev.webify.repository.PostRepository;
import com.shadaev.webify.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Value("${upload.path}")
    private String uploadPath;

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
        postRepository.save(post);

    }

    public void editPost(Post post, String title, String description,
                         MultipartFile file) throws IOException {
        post.setTitle(title);
        post.setDescription(description);
        if (!file.isEmpty()) {
            uploadImage(post, file);
        }
        postRepository.save(post);
    }

    public void uploadImage(Post post, MultipartFile file) throws IOException {
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
    }

    public void deletePost(Post post, User user) {
        user.getPosts().remove(post);
        postRepository.deleteById(post.getId());
        userRepository.save(user);
    }
}
