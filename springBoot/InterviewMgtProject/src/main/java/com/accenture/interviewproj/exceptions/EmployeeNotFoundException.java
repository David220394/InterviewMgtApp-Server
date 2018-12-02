package com.accenture.interviewproj.exceptions;

public class EmployeeNotFoundException extends Exception {
	
	private static final long serialVersionUID = -5384050002556775272L;

	public EmployeeNotFoundException(String msg) {
		super(msg);
	}
}
