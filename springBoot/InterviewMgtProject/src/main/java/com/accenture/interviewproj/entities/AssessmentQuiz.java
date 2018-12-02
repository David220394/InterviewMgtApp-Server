package com.accenture.interviewproj.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="ASSESSMENT_QUIZ_TABLE")
public class AssessmentQuiz implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="QUIZ_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long quizId;
	
	@Column(name="QUIZ_NAME")
	private String quizName;
	
	@OneToMany(mappedBy="assessmentQuiz")
	@JsonIgnore
	private Set<Job> jobs;
	
	@OneToMany(mappedBy="assessmentQuiz")
	@JsonIgnore
	private Set<QuizQuestion> quizQuestions;

	public Long getQuizId() {
		return quizId;
	}

	public void setQuizId(Long quizId) {
		this.quizId = quizId;
	}

	public String getQuizName() {
		return quizName;
	}

	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}

	public Set<Job> getJobs() {
		return jobs;
	}

	public void setJobs(Set<Job> jobs) {
		this.jobs = jobs;
	}

	public Set<QuizQuestion> getQuizQuestions() {
		return quizQuestions;
	}

	public void setQuizQuestions(Set<QuizQuestion> quizQuestions) {
		this.quizQuestions = quizQuestions;
	}
	
	
	
	
	

}
