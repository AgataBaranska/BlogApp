package com.example.BlogApp;

import com.example.BlogApp.entity.Post;
import com.example.BlogApp.service.PostService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication

public class BlogAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApplication.class, args);
	}

	@Bean
	CommandLineRunner run(PostService postService) {
		return args -> {
			Post post = Post.builder().title("Title").text("Tekst").dateTime(LocalDateTime.now()).build();
			Post post1 = Post.builder().title("Title1").text("Tekst1").dateTime(LocalDateTime.now()).build();
			Post post2 = Post.builder().title("Title2").text("Tekst2").dateTime(LocalDateTime.now()).build();
			postService.create(post);
			postService.create(post1);
			postService.create(post2);
		};


	}
}