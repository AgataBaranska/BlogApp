package com.example.BlogApp.controller;

import com.example.BlogApp.dto.input.PostDtoIn;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

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

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getPosts() throws Exception {


    }

    @Test
    void create() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/posts")
                .content(asJsonString(arrangePostDtoIn()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(EXAMPLE_TITLE))
                .andExpect(jsonPath("$.text").value(EXAMPLE_TEXT));
    }

    private PostDtoIn arrangePostDtoIn() {
        return PostDtoIn.builder()
                .text(EXAMPLE_TEXT)
                .title(EXAMPLE_TITLE)
                .build();
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}