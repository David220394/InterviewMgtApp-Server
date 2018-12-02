package com.accenture.interviewproj.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.accenture.interviewproj.enums.IntType;

@Entity
public class AssessmentInterview extends Interview {

	private static final long serialVersionUID = 5960066629682232068L;
	
	@Column(name="ASSESSMENT_INTERVIEW_DESCRIPTION")
	private String description;

	public AssessmentInterview() {
		super.type = IntType.ASSESSMENT;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
