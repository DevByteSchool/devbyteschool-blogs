package com.devbyteschool.blogs.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Integer role;

    @Override
    public String toString() {
        return "RegisterUserRequest{" +
                "fullName='" + fullName + '\'' +
                ", userName='" + userName + '\'' +
                ", role=" + role +
                '}';
    }
}
