package com.devbyteschool.blogs.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Document
public class Comment {

    @Id
    private String commentId;

    private String title;

    private String userId;

    private String blogId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


}
