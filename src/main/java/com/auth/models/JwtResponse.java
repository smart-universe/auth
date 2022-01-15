package com.auth.models;

public class JwtResponse extends GenericResponse {

	private static final long serialVersionUID = 1L;

	private boolean isValidated;

	public JwtResponse(int resultCode, String resultMessage, boolean isValidated) {
		super(resultCode, resultMessage);
		this.isValidated = isValidated;
	}

	public JwtResponse() {
		super();
	}

	public JwtResponse(int resultCode, String resultMessage) {
		super(resultCode, resultMessage);
	}

	public boolean isValidated() {
		return isValidated;
	}

	public void setValidated(boolean isValidated) {
		this.isValidated = isValidated;
	}

	@Override
	public String toString() {
		return "JwtResponse [isValidated=" + isValidated + ", getResultCode()=" + getResultCode()
				+ ", getResultMessage()=" + getResultMessage() + "]";
	}

}
