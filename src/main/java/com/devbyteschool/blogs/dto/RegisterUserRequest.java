package com.devbyteschool.blogs.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RegisterUserRequest {


    @NotBlank(message = "Full name required parameter.")
    private String fullName;

    @Email(message = "User name required parameter.")
    private String userName;

    @NotBlank(message = "Password required parameter.")
    private String password;

    @NotNull(message = "Role required parameter.")
    private List<String> roles;

    @Override
    public String toString() {
        return "RegisterUserRequest{" +
                "fullName='" + fullName + '\'' +
                ", userName='" + userName + '\'' +
                ", role=" + roles +
                '}';
    }
}
