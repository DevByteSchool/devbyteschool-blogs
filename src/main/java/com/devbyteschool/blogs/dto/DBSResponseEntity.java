package com.devbyteschool.blogs.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DBSResponseEntity<T> {

    private T data;
    private String message;

}
