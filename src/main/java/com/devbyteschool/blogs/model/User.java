package com.devbyteschool.blogs.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Document
public class User {

    @Id
    private String userId;

    private String fullName;

    private String userName;

    private String password;

    private List<String> roles;

    private Integer isSocialRegister;

    private Integer otp;

    private Integer isAcountVerify;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
