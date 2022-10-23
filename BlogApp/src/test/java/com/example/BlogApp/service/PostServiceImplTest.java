package com.example.BlogApp.service;

import com.example.BlogApp.dao.PostRepository;
import com.example.BlogApp.dto.input.PostDtoIn;
import com.example.BlogApp.dto.output.PostDtoOut;
import com.example.BlogApp.entity.Post;
import com.example.BlogApp.exception.PostNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    private static final String EXAMPLE_TEXT = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.  ulpa qui officia deserunt mollit anim id est laborum";
    private static final String EXAMPLE_TITLE = "Super story";
    private static final long ID = 1L;
    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;


    @Test
    void shouldCallRepositoryFindAll() {
        //given
        when(postRepository.findAll(ArgumentMatchers.isA(Pageable.class))).thenReturn(Page.empty());
        //when
        postService.findAll(Pageable.ofSize(2));
        //then
        verify(postRepository).findAll(ArgumentMatchers.isA(Pageable.class));
    }

    @Test
    void shouldCreatePost() {
        //given
        PostDtoIn postDtoIn = arrangePostDtoIn(null);
        Post post = Post.from(postDtoIn);
        post.setDateTime(LocalDateTime.now());
        when(postRepository.save(any(Post.class))).thenReturn(post);

        //when
        PostDtoOut savedPost = postService.create(postDtoIn);

        //then
        assertThat(savedPost).isNotNull();
        assertPost(savedPost);
    }


    @Test
    void shouldDeletePost() {
        //given
        Post post = arrangePost(ID);
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

        //when
        postService.delete(ID);

        //then
        Mockito.verify(postRepository, times(1)).deleteById(ID);
    }

    @Test
    void shouldUpdatePost() {
        //given
        PostDtoIn postDtoIn = arrangePostDtoIn(ID);
        Post post = arrangePost(ID);
        when(postRepository.findById(postDtoIn.getId())).thenReturn(Optional.of(post));
        when(postRepository.save(post)).thenReturn(post);

        //when
        PostDtoOut postDtoOut = postService.update(postDtoIn);

        //then
        Mockito.verify(postRepository, times(1)).save(post);
        assertThat(postDtoIn).isNotNull();
    }

    @Test
    void shouldThrowExceptionWhenDeletingNotExistingPost() {
        assertThrows(PostNotFoundException.class, () -> postService.delete(ID));
    }


    private void assertPost(PostDtoOut savedPost) {
        assertThat(savedPost.getDateTime()).isNotNull();
        assertThat(savedPost.getText()).isEqualTo(EXAMPLE_TEXT);
        assertThat(savedPost.getTitle()).isEqualTo(EXAMPLE_TITLE);
    }

    private Post arrangePost(Long id) {
        return Post.builder()
                .id(id)
                .title(EXAMPLE_TITLE)
                .text(EXAMPLE_TEXT)
                .build();
    }

    private PostDtoIn arrangePostDtoIn(Long id) {
        return PostDtoIn.builder()
                .id(id)
                .title(EXAMPLE_TITLE)
                .text(EXAMPLE_TEXT)
                .build();
    }
}