package com.example.BlogApp.dto.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDtoIn {
    private Long id;
    private String title;
    private String text;
    private LocalDateTime dateTime;
}
