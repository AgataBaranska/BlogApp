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
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    public static final LocalDateTime POST_CREATION_DATE = LocalDateTime.of(2022, 10, 1, 2, 2, 2);
    private static final String EXAMPLE_TEXT = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.  ulpa qui officia deserunt mollit anim id est laborum";
    private static final String EXAMPLE_TITLE = "Super story";
    private static final long ID = 1L;
    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;


    @Test
    void shouldCallRepositoryFindAll() {
        //when
        postService.findAll(Pageable.ofSize(2));
        //then
        verify(postRepository).findAll(ArgumentMatchers.isA(Pageable.class));
    }

    @Test
    void shouldCreatePost() {
        //given
        Post post = arrangePost(null);
        when(postRepository.save(post)).thenReturn(post);

        //when
        Post savedPost = postService.create(post);

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
    void shouldThrowExceptionWhenDeletingNotExistingPost(){
       assertThrows(PostNotFoundException.class,()->postService.delete(ID));
    }


    private void assertPost(Post savedPost) {
        assertThat(savedPost.getDateTime()).isEqualTo(POST_CREATION_DATE);
        assertThat(savedPost.getText()).isEqualTo(EXAMPLE_TEXT);
        assertThat(savedPost.getTitle()).isEqualTo(EXAMPLE_TITLE);
    }

    private Post arrangePost(Long id) {
        return Post.builder()
                .id(id)
                .title(EXAMPLE_TITLE)
                .text(EXAMPLE_TEXT)
                .dateTime(POST_CREATION_DATE)
                .build();
    }
    private PostDtoIn arrangePostDtoIn(Long id) {
        return PostDtoIn.builder()
                .id(id)
                .title(EXAMPLE_TITLE)
                .text(EXAMPLE_TEXT)
                .dateTime(POST_CREATION_DATE)
                .build();
    }
}