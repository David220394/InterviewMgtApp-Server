package com.accenture.interviewproj.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.accenture.interviewproj.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TABLE_EMPLOYEE")
public class Employee implements Serializable{
	
	private static final long serialVersionUID = 6764477767635148623L;

	@Id
	@Column(name = "EMPLOYEE_ID")
	private String employeeId;
	
	@Column(name = "EMPLOYEE_NAME", nullable = false)
	private String employeeName;
	
	@Column(name = "EMPLOYEE_PASSWORD", nullable = false)
	private String employeePassword;
	
	@Column(name = "ROLE")
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "TABLE_JOB_EMPLOYEE", 
	joinColumns = { @JoinColumn(name = "EMPLOYEE_EMPLOYEE_ID")},
	inverseJoinColumns = { @JoinColumn(name = "JOB_JOB_ID")})
	private List<Job> jobs;
	
	@OneToMany(mappedBy = "employee")
	@JsonIgnore
	private Set<Tracking> trackings;

	public List<Job> getJobs() {
		return jobs;
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}

	public Set<Tracking> getTrackings() {
		return trackings;
	}

	public void setTrackings(Set<Tracking> trackings) {
		this.trackings = trackings;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeePassword() {
		return employeePassword;
	}

	public void setEmployeePassword(String employeePassword) {
		this.employeePassword = employeePassword;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
