package com.accenture.interviewproj.dtos;

import java.util.List;

public class QuizDto {
	
	String quizName;
	
	List<QuestionDto> questions;

	public String getQuizName() {
		return quizName;
	}

	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}

	public List<QuestionDto> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionDto> questions) {
		this.questions = questions;
	}

	
	
	

}
