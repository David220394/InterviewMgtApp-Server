package com.accenture.interviewproj.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="TABLE_CANDIDATE_EXPERIENCE")
public class CandidateExperience implements Serializable {
	
	private static final long serialVersionUID = -1531993904282123130L;

	@Id
	@Column(name="EXPERIENCE_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long experienceId;
	
	@Column(name="EXPERIENCE_NAME")
	private String experienceName;
	
	@Column(name="SPECIALITY")
	private String specialty;
	
	@Column(name="LOCATION")
	private String location;
	
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "CANDIDATE_ID")
	@JsonIgnore
	private Candidate candidate;

	public Long getExperienceId() {
		return experienceId;
	}

	public void setExperienceId(Long experienceId) {
		this.experienceId = experienceId;
	}

	public String getExperienceName() {
		return experienceName;
	}

	public void setExperienceName(String experienceName) {
		this.experienceName = experienceName;
	}

	

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

}

