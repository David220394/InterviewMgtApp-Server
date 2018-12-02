package com.accenture.interviewproj.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.accenture.interviewproj.enums.IntType;

@Entity
public class TechnicalInterview extends Interview {
	
	private static final long serialVersionUID = -7268031684349052656L;
	
	@Column(name="TECHNICAL_INTERVIEW_FEEDBACK")
	private String feedback;
	
	@Column(name="INTERVIEWER")
	private String interviewer;
	
	public TechnicalInterview() {
		super.type = IntType.TECH;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public String getInterviewer() {
		return interviewer;
	}

	public void setInterviewer(String interviewer) {
		this.interviewer = interviewer;
	}
	
	

}
