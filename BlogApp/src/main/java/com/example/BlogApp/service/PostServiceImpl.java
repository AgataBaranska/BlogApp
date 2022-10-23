package com.example.BlogApp.service;

import com.example.BlogApp.dao.PostRepository;
import com.example.BlogApp.dto.input.PostDtoIn;
import com.example.BlogApp.dto.output.PostDtoOut;
import com.example.BlogApp.entity.Post;
import com.example.BlogApp.exception.PostNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public Page<PostDtoOut> findAll(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        List<PostDtoOut> postsDtoOut = posts.stream().map(Post::toDtoOut).collect(Collectors.toList());
        return new PageImpl<>(postsDtoOut);
    }

    @Transactional
    @Override
    public PostDtoOut create(PostDtoIn postDtoIn) {
        Post post = Post.from(postDtoIn);
        post.setDateTime(LocalDateTime.now());
        return postRepository.save(post).toDtoOut();
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Post postToDelete = findPostById(id);
        postRepository.deleteById(postToDelete.getId());
    }

    @Transactional
    @Override
    public PostDtoOut update(PostDtoIn postDtoIn) {
        Post postToUpdate = findPostById(postDtoIn.getId());
        postToUpdate.update(postDtoIn);
        Post updatedPost = postRepository.save(postToUpdate);
        return updatedPost.toDtoOut();
    }

    private Post findPostById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        return post.orElseThrow(() -> new PostNotFoundException(id));
    }
}
