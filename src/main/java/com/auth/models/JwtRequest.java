package com.auth.models;

import javax.validation.constraints.NotBlank;

public class JwtRequest {

	@NotBlank(message = "JWT token should not be Empty")
	public String jwt;

	public JwtRequest() {

	}

	public JwtRequest(String jwt) {
		this.jwt = jwt;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

}
