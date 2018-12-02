package com.accenture.interviewproj.exceptions;

public class EmployeeAlreadyExistsException extends Exception {
	
private static final long serialVersionUID = -3107291211523795974L;
	
	public EmployeeAlreadyExistsException (String msg) {
		super(msg);
	}
}
