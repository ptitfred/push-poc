package com.sfeir.websockets.jcd.shared;

import java.io.Serializable;

public class User implements Serializable {
	
	private static final long serialVersionUID = 3223131043662098627L;
	
	private int id;
	private String username;
	private String password;
	private Profile profile;
	private String notes;

	public User() {}
	
	public User(int id, String username, String password, Profile profile, String notes) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.profile = profile;
		this.notes = notes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}

}
