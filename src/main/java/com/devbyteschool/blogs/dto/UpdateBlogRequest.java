package com.devbyteschool.blogs.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UpdateBlogRequest {

    @NotBlank(message = "BlogId is required parameter.")
    private String blogId;

    @NotBlank(message = "Title is required parameter.")
    private String title;

    @NotBlank(message = "Description is required parameter.")
    private String description;

    @NotNull(message = "Publish is required parameter.")
    private Boolean publish;

    @NotBlank(message = "UserId is required parameter.")
    private String userId;

}
