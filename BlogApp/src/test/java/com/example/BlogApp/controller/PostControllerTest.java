package com.example.BlogApp.controller;

import com.example.BlogApp.dao.PostRepository;
import com.example.BlogApp.dto.input.PostDtoIn;
import com.example.BlogApp.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {
    public static final LocalDateTime POST_CREATION_DATE = LocalDateTime.of(2022, 10, 1, 2, 2, 2);
    public static final MediaType APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON;
    private static final String EXAMPLE_TEXT = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.  ulpa qui officia deserunt mollit anim id est laborum";
    private static final String EXAMPLE_TITLE = "Super story";
    private static MockHttpServletRequest request;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private  PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
   void setup() {
        PostDtoIn post = PostDtoIn.builder().title("Title").text("Tekst").build();
        PostDtoIn post1 = PostDtoIn.builder().title("Title1").text("Tekst1").build();
        PostDtoIn post2 = PostDtoIn.builder().title("Title2").text("Tekst2").build();
        PostDtoIn post3 = PostDtoIn.builder().title("Title3").text("Tekst3").build();

        postService.create(post);
        postService.create(post1);
        postService.create(post2);
        postService.create(post3);
    }

    @AfterEach
    void teardown(){
        postRepository.deleteAll();
    }

    @ParameterizedTest(name="{0}")
    @MethodSource("parametersForTestingShouldTestPagination")
    void shouldTestPagination(String name, String size, String page, Integer expectedElementsCount) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts")
                .queryParam("size", size)
                .queryParam("page", page)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(expectedElementsCount)));
    }

    private static Stream<Arguments> parametersForTestingShouldTestPagination(){
        return  Stream.of(
                Arguments.of("Should return 2 elements on second page when pagination parameters are size=2 page=1", "2", "1",2),
                Arguments.of("Should return 0 elements on third page when pagination parameters are size=2 page=2", "2", "2",0),
                Arguments.of("Should return 0 elements on first page when pagination parameters are size=3 page=0", "3", "0",3),
                Arguments.of("Should return 1 elements on second page when pagination parameters are size=3 page=1", "3", "1",1)
        );
    }

    @Test
    void shouldCreatePost() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/posts")
                .content(asJsonString(arrangePostDtoIn()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(EXAMPLE_TITLE))
                .andExpect(jsonPath("$.text").value(EXAMPLE_TEXT));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    private PostDtoIn arrangePostDtoIn() {
        return PostDtoIn.builder()
                .text(EXAMPLE_TEXT)
                .title(EXAMPLE_TITLE)
                .build();
    }
}