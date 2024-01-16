package com.devbyteschool.blogs.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
public class LoginUserRequest {

	@NotBlank(message = "User name is required parameter.")
	private String userName;

	@NotBlank(message = "Password is required parameter.")
	private String password;

	@NotNull(message = "Role is required parameter.")
	private Integer role;

	private int isSocialRegister;

	@Override
	public String toString() {
		return "LoginUserRequest{" +
				"userName='" + userName + '\'' +
				", role=" + role +
				", isSocialRegister=" + isSocialRegister +
				'}';
	}
}
