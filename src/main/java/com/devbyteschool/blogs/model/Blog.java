package com.devbyteschool.blogs.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Document
public class Blog {

    @Id
    private ObjectId blogId;

    private String title;

    private String description;

    private Boolean publish;

    private ObjectId userId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
