package com.accenture.interviewproj.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TABLE_REQUIREMENT")
public class Requirement implements Serializable {
	private static final long serialVersionUID = -1335276957840542825L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "REQUIREMENT_ID")
	private Long requirementId;

	@Column(name = "NAME")
	private String name;

	@ManyToMany(mappedBy = "requirements", fetch = FetchType.EAGER)
	@JsonIgnore
	private List<Job> jobs;
	

	public List<Job> getJobs() {
		return jobs;
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}

	public Long getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(Long requirementId) {
		this.requirementId = requirementId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
