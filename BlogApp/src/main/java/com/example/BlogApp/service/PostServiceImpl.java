package com.example.BlogApp.service;

import com.example.BlogApp.dao.PostRepository;
import com.example.BlogApp.entity.Post;
import com.example.BlogApp.exception.PostNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Post create(Post postEntry) {
        return postRepository.save(postEntry);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Post postToDelete = findPostById(id);
        postRepository.deleteById(postToDelete.getId());
    }

    @Transactional
    @Override
    public Post update(Post postEntry) {
        Post postToUpdate = findPostById(postEntry.getId());
        postRepository.flush();
        return postToUpdate;
    }


    private Post findPostById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        return post.orElseThrow(() -> new PostNotFoundException(id));
    }
}
