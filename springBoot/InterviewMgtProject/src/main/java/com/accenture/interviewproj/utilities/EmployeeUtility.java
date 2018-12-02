package com.accenture.interviewproj.utilities;

import com.accenture.interviewproj.dtos.EmployeeDto;
import com.accenture.interviewproj.entities.Employee;
import com.accenture.interviewproj.enums.Role;

public class EmployeeUtility {
	
	public static Employee convertEmployeeDtoToEmployee(EmployeeDto registerHRDto) {
		Employee employee = new Employee();
		employee.setEmployeeId(registerHRDto.getEmployeeId());
		employee.setEmployeeName(registerHRDto.getEmployeeName());
		employee.setEmployeePassword(registerHRDto.getEmployeePassword());
		employee.setRole(Role.HR);
		return employee;
	}

}
