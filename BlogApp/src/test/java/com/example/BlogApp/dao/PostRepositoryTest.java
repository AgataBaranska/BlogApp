package com.example.BlogApp.dao;

import com.example.BlogApp.entity.Post;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class PostRepositoryTest {

    private static final String EXAMPLE_TEXT = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.  ulpa qui officia deserunt mollit anim id est laborum";
    private static final String EXAMPLE_TITLE = "Super story";
    @Autowired
    private PostRepository postRepository;

    @AfterEach
    void teardown() {
        postRepository.deleteAll();
    }

    @Test
    void shouldReturnPostByTitle() {
        //given
        Post post = Post.builder()
                .text(EXAMPLE_TEXT)
                .title(EXAMPLE_TITLE)
                .dateTime(LocalDateTime.now()).build();
        postRepository.save(post);

        //when
        Post savedPost = postRepository.findByTitle(EXAMPLE_TITLE);

        //then
        assertThat(savedPost).isNotNull();
    }

}