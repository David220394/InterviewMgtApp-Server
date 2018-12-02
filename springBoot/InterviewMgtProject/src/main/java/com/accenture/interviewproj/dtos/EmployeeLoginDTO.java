package com.accenture.interviewproj.dtos;

import java.io.Serializable;

public class EmployeeLoginDTO implements Serializable {

	private static final long serialVersionUID = -2399359065988055560L;

	private String employeeId;
	private String employeePassword;
	
	public EmployeeLoginDTO() {
	}

	public EmployeeLoginDTO(String employeeId, String employeePassword) {
		this.employeeId = employeeId;
		this.employeePassword = employeePassword;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeePassword() {
		return employeePassword;
	}

	public void setEmployeePassword(String employeePassword) {
		this.employeePassword = employeePassword;
	}

}
