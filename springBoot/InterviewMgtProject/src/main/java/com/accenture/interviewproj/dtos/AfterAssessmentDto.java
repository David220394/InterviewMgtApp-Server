package com.accenture.interviewproj.dtos;

import java.util.List;

public class AfterAssessmentDto {
	
	private String quizName;
	
	private double maxScore;
	
	private String interviewLink;
	
	private List<AfterAssessmentQuestionDto> afterAssessmentQuestionDtos;

	public List<AfterAssessmentQuestionDto> getAfterAssessmentQuestionDtos() {
		return afterAssessmentQuestionDtos;
	}

	public void setAfterAssessmentQuestionDtos(List<AfterAssessmentQuestionDto> afterAssessmentQuestionDtos) {
		this.afterAssessmentQuestionDtos = afterAssessmentQuestionDtos;
	}


	public String getInterviewLink() {
		return interviewLink;
	}

	public void setInterviewLink(String interviewLink) {
		this.interviewLink = interviewLink;
	}

	public String getQuizName() {
		return quizName;
	}

	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}

	public double getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(double maxScore) {
		this.maxScore = maxScore;
	}

	
}
