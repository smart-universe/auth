package com.auth.models;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

public class UserCreationRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "username is manditory")
	private String username;

	@NotBlank(message = "password is manditory")
	private String password;

	@NotBlank(message = "emailId is manditory")
	private String emailId;

	@NotBlank(message = "firstName is manditory")
	private String firstName;

	@NotBlank(message = "LastName is manditory")
	private String LastName;

	@NotBlank(message = "country is manditory")
	private String country;

	public UserCreationRequest( String username, String password, String emailId, String firstName,
			String lastName, String country) {
		super();
		this.username = username;
		this.password = password;
		this.emailId = emailId;
		this.firstName = firstName;
		LastName = lastName;
		this.country = country;
	}

	public UserCreationRequest() {
		super();

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "UserCreationRequest [username=" + username + ", password=" + "*************"
				+ ", emailId=" + emailId + ", firstName=" + firstName + ", LastName=" + LastName + ", country="
				+ country + "]";
	}

}
