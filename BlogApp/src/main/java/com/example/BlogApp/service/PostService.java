package com.example.BlogApp.service;

import com.example.BlogApp.dto.input.PostDtoIn;
import com.example.BlogApp.dto.output.PostDtoOut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    Page<PostDtoOut> findAll(Pageable pageable);
    PostDtoOut create(PostDtoIn PostDtoIn);
    void delete(Long id);
    PostDtoOut update(PostDtoIn postDtoIn);



}
