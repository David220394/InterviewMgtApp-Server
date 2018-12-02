package com.accenture.interviewproj.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="TABLE_STATUS")
public class Status implements Serializable{

	private static final long serialVersionUID = -5346198218741189722L;

	@Id
	@Column(name="STATUS_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long statusId;
	
	@Column(name="STATUS_NAME")
	private String statusName;
	
	@Column(name="STATUS_CHANGE_DATE")
	private LocalDateTime creationDate;
	
	@ManyToOne
	@JoinColumn(name="JOB_CANDIDATE_ID")
	private JobCandidate jobCandidate;

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public JobCandidate getCandidate() {
		return jobCandidate;
	}

	public void setCandidate(JobCandidate jobCandidate) {
		this.jobCandidate = jobCandidate;
	}
	
	@PrePersist
	public void initCreatedDate() {
		this.creationDate = LocalDateTime.now();
	}

	
	
}
