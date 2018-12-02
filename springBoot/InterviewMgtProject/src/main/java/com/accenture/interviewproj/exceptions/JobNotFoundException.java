package com.accenture.interviewproj.exceptions;

public class JobNotFoundException extends Exception {
	
	private static final long serialVersionUID = -5384050002556775272L;

	public JobNotFoundException(String msg) {
		super(msg);
	}
}
