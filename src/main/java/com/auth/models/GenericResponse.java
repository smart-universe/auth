package com.auth.models;

import java.io.Serializable;

public class GenericResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private int resultCode;

	private String resultMessage;

	public GenericResponse(int resultCode, String resultMessage) {
		super();
		this.resultCode = resultCode;
		this.resultMessage = resultMessage;
	}

	public GenericResponse() {
		super();

	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	@Override
	public String toString() {
		return "GenericResponse [resultCode=" + resultCode + ", resultMessage=" + resultMessage + "]";
	}

}
