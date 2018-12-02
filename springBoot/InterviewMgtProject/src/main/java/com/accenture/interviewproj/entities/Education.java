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
@Table(name="TABLE_CANDIDATE_EDUCATION")
public class Education implements Serializable {

	private static final long serialVersionUID = 6084763771332496875L;

	@Id
	@Column(name="EDUCATION_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="INSTITUTION_NAME")
	private String institutionName;
	
	@Column(name="PROGRAM_STUDY")
	private String programStudy;
	
	@Column(name="GRADE")
	private double grade;
	
	
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "CANDIDATE_ID")
	@JsonIgnore
	private Candidate candidate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInstitutionName() {
		return institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	public String getProgramStudy() {
		return programStudy;
	}

	public void setProgramStudy(String programStudy) {
		this.programStudy = programStudy;
	}

	public double getGrade() {
		return grade;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

}
