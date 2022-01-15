package com.auth.models;

import javax.validation.constraints.NotBlank;

public class AuthenticationRequest {
	
	@NotBlank(message = "username should not be empty")
	private String username;
	
	@NotBlank(message = "password should not be empty")
	private String password;

	public AuthenticationRequest() {

	}

	public AuthenticationRequest(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUserName(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
