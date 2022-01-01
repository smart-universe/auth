package com.auth.models;

public class UserNameResponse {

	private boolean Exists;

	private String Message;

	public boolean isExists() {
		return Exists;
	}

	public void setExists(boolean exists) {
		Exists = exists;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	@Override
	public String toString() {
		return "UserNameResponse [Exists=" + Exists + ", Message=" + Message + "]";
	}

}
