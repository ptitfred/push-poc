package com.sfeir.websockets.jcd.shared;

import java.io.Serializable;

public class User implements Serializable {
	
	private static final long serialVersionUID = 3223131043662098627L;
	
	public static enum Language {
		FRENCH, ENGLISH;
	}
	
	private int id;
	private String username;
	private String fullName;
	private Profile profile;
	private String city;
	private Language language;
	private String notes;

	public User() {}
	
	public User(int id, String username, String fullName, Profile profile, String city, Language language, String notes) {
		super();
		this.id = id;
		this.username = username;
		this.fullName = fullName;
		this.city = city;
		this.language = language;
		this.profile = profile;
		this.notes = notes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

}
