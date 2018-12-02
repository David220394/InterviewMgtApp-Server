package com.accenture.interviewproj.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.accenture.interviewproj.enums.Track;

@Entity
@Table(name = "TABLE_TRACKING")
public class Tracking implements Serializable{
	
	private static final long serialVersionUID = 656339970700670426L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TRACKING_ID")
	private Long trackingId;
	
	@Column(name = "CREATION_DATE")
	@CreationTimestamp
	private LocalDateTime creationDate;
	
	@Column(name = "TRACKING_TYPE")
	@Enumerated(EnumType.STRING)
	private Track trackingType;
	
	@Column(name = "COMMENT")
	private String comment;
	
	@ManyToOne
	@JoinColumn(name = "EMPLOYEE_ID")
	private Employee employee;
	
	@ManyToOne
	@JoinColumn(name= "CANDIDATE_ID")
	private Candidate candidate;
	
	@ManyToOne
	@JoinColumn(name= "JOB_ID")
	private Job job;

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public Long getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(Long trackingId) {
		this.trackingId = trackingId;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public Track getTrackingType() {
		return trackingType;
	}

	public void setTrackingType(Track trackingType) {
		this.trackingType = trackingType;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@PrePersist
	public void initCreatedDate() {
		this.creationDate = LocalDateTime.now();
	}

}
