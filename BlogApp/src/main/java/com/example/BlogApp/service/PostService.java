package com.example.BlogApp.service;

import com.example.BlogApp.dto.input.PostDtoIn;
import com.example.BlogApp.dto.output.PostDtoOut;
import com.example.BlogApp.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    Page<Post> findAll(Pageable pageable);
    Post create(Post postEntry);
    void delete(Long id);
    PostDtoOut update(PostDtoIn postDtoIn);



}
