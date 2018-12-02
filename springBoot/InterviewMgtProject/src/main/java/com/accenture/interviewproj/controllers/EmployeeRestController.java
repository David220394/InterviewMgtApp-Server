package com.accenture.interviewproj.controllers;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.interviewproj.dtos.EmployeeDto;
import com.accenture.interviewproj.entities.Employee;
import com.accenture.interviewproj.exceptions.EmployeeAlreadyExistsException;
import com.accenture.interviewproj.exceptions.EmployeeNotFoundException;
import com.accenture.interviewproj.exceptions.IdNotFoundException;
import com.accenture.interviewproj.services.EmployeeService;
import com.accenture.interviewproj.utilities.EmployeeUtility;

@RestController
@RequestMapping("api/employee")
@CrossOrigin
public class EmployeeRestController {
	
	private EmployeeService employeeService;

	public EmployeeRestController(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}
	
	/**
	 * 
	 * @param employeeDto
	 * @return Employee
	 * Insert Employee
	 */
	@PostMapping("/insertEmployee")
	@Secured(value="ROLE_ADMIN")
	public ResponseEntity<?> insertEmployee(@RequestBody EmployeeDto employeeDto) {
		try {
			
			Employee employee = EmployeeUtility.convertEmployeeDtoToEmployee(employeeDto);
			return ResponseEntity.ok(employeeService.insertHR(employee));
		} catch (EmployeeAlreadyExistsException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Employee name already exists");
		}
	}
	
	/**
	 * 
	 * @Get the list of all employees
	 */
	@GetMapping("/")
	public ResponseEntity<List<?>> getAllEmployees() {
		return ResponseEntity.ok(employeeService.findAll());
	}
	
	/**
	 * 
	 * @param employeeName
	 * Find a employee by its name
	 */
	@GetMapping("/{employeeName}")
	public ResponseEntity<?> findEmployeeByName(@PathVariable("employeeName") String employeeName) {
		Employee employee = employeeService.findByEmployeeName(employeeName);
		if (employee != null) {
			return ResponseEntity.ok(employee);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * 
	 * @param employeeId
	 * @return employee
	 * find an employee by its employee id
	 */
	@GetMapping("/{employeeId}")
	public ResponseEntity<?> findEmployeeById(@PathVariable("employeeId") String employeeId) {
		Employee employee;
		try {
			employee = employeeService.findById(employeeId);
			return ResponseEntity.ok(employee);
		} catch (IdNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * 
	 * @param employeeName
	 * Delete an employee by its name
	 */
	@DeleteMapping("/{employeeName}")
	public HttpEntity<String> deleteJob(@PathVariable("employeeName") String employeeName) {
		try {
			employeeService.deleteEmployee(employeeName);
			return ResponseEntity.ok("DELETED!!");
		} catch (EmployeeNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param employee
	 * Update an employee
	 */
	@PutMapping("/")
	public ResponseEntity<?> updateEmployeeRole(@RequestBody String employeeId) {
		try {
			employeeService.updateEmployeeRole(employeeId);
			return ResponseEntity.ok("Updated");
		} catch (EmployeeNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
}
