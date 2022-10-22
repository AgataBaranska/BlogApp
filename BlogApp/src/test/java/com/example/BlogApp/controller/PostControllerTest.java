package com.example.BlogApp.controller;

import com.example.BlogApp.entity.Post;
import com.example.BlogApp.service.PostService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@SpringBootTest
class PostControllerTest {

    @Autowired
    private PostController postController;

    @Mock
    PostService postService;
    @Autowired
    private MockMvc mockMvc;
    private static final String EXAMPLE_TEXT ="Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.  ulpa qui officia deserunt mollit anim id est laborum";
    private static final String EXAMPLE_TITLE ="Super story";

@Disabled
    @Test
    public void contextLoads() throws Exception {
        assertThat(postController).isNotNull();
    }
    @Disabled
    @Test
    void getPosts() throws Exception {
        List<Post> listPosts = new ArrayList<>();
        listPosts.add(Post.builder()
                .text(EXAMPLE_TEXT)
                .title(EXAMPLE_TITLE)
                .dateTime(LocalDateTime.now()).build());
        listPosts.add(Post.builder()
                .text(EXAMPLE_TEXT)
                .title(EXAMPLE_TITLE)
                .dateTime(LocalDateTime.now()).build());
        Page<Post> listPostsPage = new PageImpl<Post>(listPosts);
        Mockito.when(postService.findAll(ArgumentMatchers.isA(Pageable.class))).thenReturn(listPostsPage);
        String url ="/posts";
        mockMvc.perform(get(url)).andExpect(status().isOk());
    }

    @Disabled
    @Test
    void create() {
      Post post =  Post.builder()
                .text(EXAMPLE_TEXT)
                .title(EXAMPLE_TITLE)
                .dateTime(LocalDateTime.now()).build();

    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}