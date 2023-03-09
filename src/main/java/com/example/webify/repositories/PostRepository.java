package com.example.webify.repositories;

import com.example.webify.models.Post;
import com.example.webify.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTag(String tag);
}
