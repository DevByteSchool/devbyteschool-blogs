package com.devbyteschool.blogs.dto;

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

    private String blogId;

    private String title;

    private String description;

    private Boolean publish;

    private String userId;

}
