package com.accenture.interviewproj.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="TABLE_QUESTION")
public class Question {
	
	@Id
	@Column(name = "QUESTION_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long questionId; 
	
	@Column(name = "QUESTION_NAME")
	private String question;
	
	@OneToMany(mappedBy = "question")
	@JsonIgnore
	private List<InterviewQuestion> interviewQuestions;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

	public List<InterviewQuestion> getInterviewQuestions() {
		return interviewQuestions;
	}

	public void setInterviewQuestions(List<InterviewQuestion> interviewQuestions) {
		this.interviewQuestions = interviewQuestions;
	}


}
