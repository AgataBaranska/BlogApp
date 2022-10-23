package com.example.BlogApp.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDtoOut {
    private Long id;
    private String title;
    private String text;
    private LocalDateTime dateTime;
}
