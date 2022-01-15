package com.auth.models;

public class UsernameValidationResponse extends GenericResponse {

	private static final long serialVersionUID = 1L;

	private boolean isExists;

	public UsernameValidationResponse(int resultCode, String resultMessage, boolean isExists) {
		super(resultCode, resultMessage);
		this.isExists = isExists;
	}

	public UsernameValidationResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UsernameValidationResponse(int resultCode, String resultMessage) {
		super(resultCode, resultMessage);
		// TODO Auto-generated constructor stub
	}

	public boolean isExists() {
		return isExists;
	}

	public void setExists(boolean isExists) {
		this.isExists = isExists;
	}

	@Override
	public String toString() {
		return "UsernameValidationResponse [isExists=" + isExists + ", getResultCode()=" + getResultCode()
				+ ", getResultMessage()=" + getResultMessage() + "]";
	}

}
