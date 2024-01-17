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
public class User {

    @Id
    private String userId;

    private String fullName;

    private String userName;

    private String password;

    private Integer role;

    private Integer isSocialRegister;

    private Integer otp;

    private Integer isAcountVerify;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
