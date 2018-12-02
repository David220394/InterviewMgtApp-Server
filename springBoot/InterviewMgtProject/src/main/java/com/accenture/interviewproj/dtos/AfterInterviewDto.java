package com.accenture.interviewproj.dtos;

import java.util.List;

public class AfterInterviewDto {
	
	private String link;
	
	private String type;
	
	private Double score;
	
	private Double maxScore;
	
	private String feedback;
	
	private List<InterviewQuestionDto> questions;

	
	public List<InterviewQuestionDto> getQuestions() {
		return questions;
	}

	public void setQuestions(List<InterviewQuestionDto> questions) {
		this.questions = questions;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(Double maxScore) {
		this.maxScore = maxScore;
	}

}
