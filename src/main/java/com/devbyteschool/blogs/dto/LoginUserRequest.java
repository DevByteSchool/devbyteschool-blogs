package com.devbyteschool.blogs.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
public class LoginUserRequest {

	@NotBlank(message = "User name is required parameter.")
	private String userName;

	@NotBlank(message = "Password is required parameter.")
	private String password;

	private int isSocialRegister;

	@Override
	public String toString() {
		return "LoginUserRequest{" +
				"userName='" + userName + '\'' +
				", isSocialRegister=" + isSocialRegister +
				'}';
	}
}

