package com.accenture.interviewproj.dtos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class JobDto {
	
	private String projectName;
	
	private String position;
	
	private String location;
	
	//private Integer noOfVacancy;
	
	//@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime closingDate;

	//@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime creationDate;
	
	private List<String> assignTos = new ArrayList<>();
	
	private List<String> requirements;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public LocalDateTime getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(LocalDateTime closingDate) {
		this.closingDate = closingDate;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public List<String> getAssignTos() {
		return assignTos;
	}

	public void setAssignTos(List<String> assignTos) {
		this.assignTos = assignTos;
	}

	public List<String> getRequirements() {
		return requirements;
	}

	public void setRequirements(List<String> requirements) {
		this.requirements = requirements;
	}

	
}
