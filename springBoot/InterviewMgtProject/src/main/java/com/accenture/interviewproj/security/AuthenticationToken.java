package com.accenture.interviewproj.security;

import java.io.Serializable;

public class AuthenticationToken implements Serializable {

	private static final long serialVersionUID = 7551402310132669463L;

	private String token;

	public AuthenticationToken(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
