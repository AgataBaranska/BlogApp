package com.example.BlogApp.controller;

import com.example.BlogApp.dto.input.PostDtoIn;
import com.example.BlogApp.dto.output.PostDtoOut;
import com.example.BlogApp.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    @GetMapping
    public ResponseEntity<Page<PostDtoOut>> getPosts(Pageable pageable){
       return ResponseEntity.ok().body(postService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<PostDtoOut> create(@RequestBody PostDtoIn postDtoIn){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/posts/").toUriString());
        return ResponseEntity.created(uri).body(postService.create(postDtoIn));
    }

    @PutMapping
    public ResponseEntity<PostDtoOut> update(@RequestBody PostDtoIn postDtoIn){
        return ResponseEntity.ok().body(postService.update(postDtoIn));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id){
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
