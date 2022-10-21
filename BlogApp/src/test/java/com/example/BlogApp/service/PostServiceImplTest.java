package com.example.BlogApp.service;

import com.example.BlogApp.dao.PostRepository;
import com.example.BlogApp.entity.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    private static final String EXAMPLE_TEXT ="Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.  ulpa qui officia deserunt mollit anim id est laborum";
    private static final String EXAMPLE_TITLE ="Super story";

    private PostService postService;
    @BeforeEach
    void setUp() {
        postService = new PostServiceImpl(postRepository);
    }

    @Test
    void canFindAllPosts() {
        Page<Post> pagedPosts = Mockito.mock(Page.class);
        Mockito.when(postRepository.findAll(ArgumentMatchers.isA(Pageable.class))).thenReturn(pagedPosts);
        verify(postRepository).findAll(ArgumentMatchers.isA(Pageable.class));
    }

    @Test
    void canCreatePost() {
        Post post = Post.builder().title(EXAMPLE_TITLE).text(EXAMPLE_TEXT).dateTime(LocalDateTime.now()).build();
        Post savedPost= postService.create(post);
        assertThat(savedPost).isNotNull();
    }

    @Test
    void canDeletePost() {
        Post post = Post.builder()
                .text(EXAMPLE_TEXT)
                .title(EXAMPLE_TITLE)
                .dateTime(LocalDateTime.now()).build();
       Post postCreated = postService.create(post);
       postService.delete(postCreated.getId());
       Mockito.verify(postRepository).deleteById(postCreated.getId());
    }

    @Test
    void update() {
        Post post = Post.builder()
                .text(EXAMPLE_TEXT)
                .title(EXAMPLE_TITLE)
                .dateTime(LocalDateTime.now()).build();
        Post postCreated = postService.create(post);
        postService.update(postCreated);
        Mockito.verify(postRepository).save(postCreated);
    }
}