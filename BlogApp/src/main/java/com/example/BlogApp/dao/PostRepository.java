package com.example.BlogApp.dao;

import com.example.BlogApp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
    Post findByTitle(String title);
}
