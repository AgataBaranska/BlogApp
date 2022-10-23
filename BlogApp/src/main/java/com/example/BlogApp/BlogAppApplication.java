package com.example.BlogApp;

import com.example.BlogApp.service.PostService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication

public class BlogAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApplication.class, args);
	}

	@Bean
	CommandLineRunner run(PostService postService) {
		return args -> {
//			PostDtoIn post = PostDtoIn.builder().title("Title").text("Tekst").build();
//			PostDtoIn post1 = PostDtoIn.builder().title("Title1").text("Tekst1").build();
//			PostDtoIn post2 = PostDtoIn.builder().title("Title2").text("Tekst2").build();
//			postService.create(post);
//			postService.create(post1);
//			postService.create(post2);
		};


	}
}