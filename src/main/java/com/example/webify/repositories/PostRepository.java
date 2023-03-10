package com.example.webify.repositories;

import com.example.webify.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByHeader(String header);
}
