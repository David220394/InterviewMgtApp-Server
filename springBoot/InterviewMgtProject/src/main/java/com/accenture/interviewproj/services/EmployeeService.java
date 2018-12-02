package com.accenture.interviewproj.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.accenture.interviewproj.entities.Employee;
import com.accenture.interviewproj.entities.Job;
import com.accenture.interviewproj.enums.Role;
import com.accenture.interviewproj.exceptions.EmployeeAlreadyExistsException;
import com.accenture.interviewproj.exceptions.EmployeeNotFoundException;
import com.accenture.interviewproj.exceptions.IdNotFoundException;
import com.accenture.interviewproj.repositories.EmployeeRepository;

@Service(value = "employeeService")
public class EmployeeService implements UserDetailsService {

	private final EmployeeRepository employeeRepository;
	private final BCryptPasswordEncoder bcryptEncoder;

	public EmployeeService(EmployeeRepository employeeRepository, BCryptPasswordEncoder bcryptEncoder) {
		this.employeeRepository = employeeRepository;
		this.bcryptEncoder = bcryptEncoder;
	}

	public void createEmployee(Employee employee) {
		employeeRepository.save(employee);
	}

	/**
	 * 
	 * @param eid
	 * @throws IdNotFoundException
	 *             Find an employee by id
	 */
	public Employee findById(String eid) throws IdNotFoundException {
		Employee employee = employeeRepository.getOne(eid);
		if (employee != null) {
			return employee;
		} else {
			throw new IdNotFoundException("Employee cannot be found");
		}
	}

	/**
	 * 
	 * @param employee
	 * @Registering HR
	 * @throws EmployeeAlreadyExistsException
	 */
	public Employee insertHR(Employee employee) throws EmployeeAlreadyExistsException {
		if (employeeRepository.findByEmployeeName(employee.getEmployeeName()) == null) {
			employee.setEmployeePassword(bcryptEncoder.encode(employee.getEmployeePassword()));
			return employeeRepository.save(employee);
		} else {
			throw new EmployeeAlreadyExistsException("This job name already exists");
		}
	}

	/**
	 * 
	 * @param employeeName
	 * @return employee search by employee name
	 */
	public Employee findByEmployeeName(String employeeName) {
		return employeeRepository.findByEmployeeName(employeeName);
	}

	/**
	 * 
	 * @return list of all registered HR
	 */
	public List<Employee> findAll() {
		return employeeRepository.findAll();
	}

	/**
	 * 
	 * @param employeeName
	 * @throws EmployeeNotFoundException
	 *             Delete employee by employee name
	 */
	public void deleteEmployee(String employeeName) throws EmployeeNotFoundException {
		Employee findEmployee = employeeRepository.findByEmployeeName(employeeName);
		if (findEmployee != null) {
			employeeRepository.delete(findEmployee);
			return;
		} else {
			throw new EmployeeNotFoundException(
					"Delete failed. No employee found with the employee Name:" + employeeName);
		}
	}

	/**
	 * 
	 * @param employee
	 * @return updated employee
	 * @throws EmployeeNotFoundException
	 */
	public Employee updateEmployeeRole(String employeeId) throws EmployeeNotFoundException {
		Employee searchEmployee = employeeRepository.findByEmployeeId(employeeId);
		if (searchEmployee != null) {
			searchEmployee.setRole(Role.ADMIN);
			return employeeRepository.save(searchEmployee);
		} else {
			throw new EmployeeNotFoundException("Failed to update Employee Details");
		}
	}

	public void updateJob(String eid, Job job) {
		Employee employee = employeeRepository.findByEmployeeId(eid);
		List<Job> jobs = employee.getJobs();
		jobs.add(job);
		employee.setJobs(jobs);
		employeeRepository.save(employee);
	}

	/******
	 * Method to verify if the username enter during login is valid If valid, return
	 * new UserDetails object If false, throws UsernameNotFoundException
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Employee employee = employeeRepository.findByEmployeeId(username);
		if (employee == null) {
			throw new UsernameNotFoundException("Invalid eid or password.");
		}
		return new User(username, employee.getEmployeePassword(), this.getAuthority(employee));
	}

	/**
	 * Get the role of a User and convert it into a SimpleGrantedAuthority
	 */
	private Set<SimpleGrantedAuthority> getAuthority(Employee employee) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		// Check if employee is an Admin
		if (employee.getRole() == Role.ADMIN) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + Role.ADMIN.toString()));
			System.out.println(authorities);
		}
		// Check if employee is a HR
		if (employee.getRole() == Role.HR) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + Role.HR.toString()));
		}
		return authorities;
	}
}
