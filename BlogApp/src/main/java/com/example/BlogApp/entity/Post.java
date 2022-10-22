package com.example.BlogApp.entity;

import com.example.BlogApp.dto.input.PostDtoIn;
import com.example.BlogApp.dto.output.PostDtoOut;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "post")
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Basic
    private String title;

    @Basic
    private String text;

    @Basic
    private LocalDateTime dateTime;

   public static Post from(PostDtoIn postDtoIn) {
        return Post.builder()
                .title(postDtoIn.getTitle())
                .text(postDtoIn.getText()).build();
    }

    public void update(PostDtoIn postDtoIn) {
        text = postDtoIn.getText();
        title= postDtoIn.getTitle();
    }

    public PostDtoOut toDtoOut() {
        return PostDtoOut.builder()
                .id(id)
                .text(text)
                .title(title)
                .dateTime(dateTime)
                .build();
    }
}
