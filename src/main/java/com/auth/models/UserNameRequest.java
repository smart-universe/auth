package com.auth.models;

public class UserNameRequest {

	private String username;

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UserNameRequest(String username) {
		super();
		this.username = username;
	}

	public UserNameRequest() {

	}

	@Override
	public String toString() {
		return "UserNameRequest [Username=" + username + "]";
	}

}
