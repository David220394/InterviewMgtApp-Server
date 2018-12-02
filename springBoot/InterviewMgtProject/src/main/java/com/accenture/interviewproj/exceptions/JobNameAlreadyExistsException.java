package com.accenture.interviewproj.exceptions;

public class JobNameAlreadyExistsException extends Exception {
	private static final long serialVersionUID = 4947818236583999679L;

	public JobNameAlreadyExistsException(String msg) {
		super(msg);
	}
}
