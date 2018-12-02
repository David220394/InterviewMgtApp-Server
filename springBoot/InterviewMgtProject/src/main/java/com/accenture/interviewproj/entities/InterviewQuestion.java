package com.accenture.interviewproj.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="TABLE_INTERVIEW_QUESTION")
public class InterviewQuestion {
	
	@Id
	@Column(name="INTERVIEW_QUESTION_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(name = "MARK")
	private int mark;
	
	@Column(name = "COMMENT")
	private String comment;
	
	@ManyToOne
	@JoinColumn(name="QUESTION_ID")
	private Question question;
	
	@ManyToOne
	@JoinColumn(name="INTERVIEW_ID")
	private Interview interview;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Interview getInterview() {
		return interview;
	}

	public void setInterview(Interview interview) {
		this.interview = interview;
	}

}
