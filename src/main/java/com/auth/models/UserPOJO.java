package com.auth.models;

import java.io.Serializable;

public class UserPOJO implements Serializable {

	/**
	 * This is the request class that should be used anywhere we need a user object
	 * .. DONT user user.java at any place.
	 */
	private static final long serialVersionUID = 1L;

	private Long userID;

	private String username;

	private String emailId;

	private String firstName;

	private String LastName;

	private String country;

	public UserPOJO(Long userID, String username, String emailId, String firstName, String lastName, String country) {
		super();
		this.userID = userID;
		this.username = username;
		this.emailId = emailId;
		this.firstName = firstName;
		LastName = lastName;
		this.country = country;
	}

	public UserPOJO() {
		super();
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
		return "UserPOJO [userID=" + userID + ", username=" + username + ", emailId=" + emailId + ", firstName="
				+ firstName + ", LastName=" + LastName + ", country=" + country + "]";
	}

}
