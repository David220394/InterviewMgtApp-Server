package com.accenture.interviewproj.dtos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JobWithIdDto {
	
	private Long jobId;
	
	private String jobName;
	
	private String position;
	
	private String location;
	
	//@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime closingDate;

	//@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime creationDate;
	
	private Boolean activeJob;
	
	private List<String> assignTo = new ArrayList<>();
	
	
	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
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

	public List<String> getAssignTo() {
		return assignTo;
	}

	public void setAssignTo(List<String> assignTo) {
		this.assignTo = assignTo;
	}

	public Boolean getActiveJob() {
		return activeJob;
	}

	public void setActiveJob(Boolean activeJob) {
		this.activeJob = activeJob;
	}

}
