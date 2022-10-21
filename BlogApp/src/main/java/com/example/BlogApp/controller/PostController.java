package com.example.BlogApp.controller;

import com.example.BlogApp.entity.Post;
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
    public ResponseEntity<Page<Post>> getPosts(Pageable pageable){
       return ResponseEntity.ok().body(postService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<Post> create(@RequestBody Post postEntry){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("").toUriString());
        return ResponseEntity.created(uri).body(postService.create(postEntry));
    }

    @PutMapping
    public ResponseEntity<Post> update (@RequestBody Post postEntry){
        return ResponseEntity.ok().body(postService.update(postEntry));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id){
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }
}