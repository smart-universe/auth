package com.auth.models;

public class AuthenticateResponse extends GenericResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String jwt;
	private UserPOJO user;

	public AuthenticateResponse(int resultCode, String resultMessage, String jwt, UserPOJO user) {
		super(resultCode, resultMessage);
		this.jwt = jwt;
		this.user = user;
	}

	public AuthenticateResponse() {
		super();
	}

	public AuthenticateResponse(int resultCode, String resultMessage) {
		super(resultCode, resultMessage);
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public UserPOJO getUser() {
		return user;
	}

	public void setUser(UserPOJO user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "AuthenticateResponse [jwt=" + jwt + ", user=" + user + "]";
	}

}
