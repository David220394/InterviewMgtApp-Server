package com.accenture.interviewproj.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.accenture.interviewproj.enums.IntType;

@Entity
public class HRInterview extends Interview{
	
	private static final long serialVersionUID = -4402467118774494768L;
	
	@Column(name="HR_INTERVIEW_FEEDBACK")
	private String feedback;
	
	@Column(name="INTERVIEWER")
	private String interviewer;

	public HRInterview() {
		super.type = IntType.HR;
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
