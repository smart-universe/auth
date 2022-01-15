package com.auth.models;

import javax.validation.constraints.NotBlank;

public class UsernameValidationRequest {

	@NotBlank(message = "username should not be blank")
	private String username;

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UsernameValidationRequest(String username) {
		super();
		this.username = username;
	}

	public UsernameValidationRequest() {

	}

	@Override
	public String toString() {
		return "UsernameValidationRequest [Username=" + username + "]";
	}

}
