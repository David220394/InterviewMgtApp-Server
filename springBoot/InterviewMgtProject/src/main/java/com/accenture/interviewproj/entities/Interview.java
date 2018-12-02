package com.accenture.interviewproj.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.accenture.interviewproj.enums.IntType;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="TABLE_INTERVIEW")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "INTERVIEW_TYPE")
public class Interview implements Serializable {
	
	private static final long serialVersionUID = 9044017024222964347L;

	@Id
	@Column(name="INTERVIEW_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long interviewId;
	
	@Column(name="COMPLETED")
	private Boolean completed;
	
	@Column(name="CREATION_DATE_TIME")
	private LocalDateTime creationDateTime;
	
	@Column(name="END_DATE_TIME")
	private LocalDateTime endDateTime;
	
	@Column(name="SCORE")
	private Double score;
	
	@Column(name="MAX_SCORE")
	private Double maxScore;
	
	@Column(name="INTERVIEW_LINK", unique=true)
	private String link;
	
	@Column(name="TYPE")
	@Enumerated(EnumType.STRING)
	protected IntType type;
	
	@ManyToOne
	@JoinColumn(name="JOB_ID")
	private Job job;
	
	@ManyToOne
	@JoinColumn(name="CANDIDATE_ID")
	private Candidate candidate;
	
	@OneToMany(mappedBy="interview")
	@JsonIgnore
	private List<InterviewQuestion> interviewQuestions;

	
	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Long getInterviewId() {
		return interviewId;
	}

	public void setInterviewId(Long interviewId) {
		this.interviewId = interviewId;
	}

	public Boolean getCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}
	
	public LocalDateTime getCreationDateTime() {
		return creationDateTime;
	}

	public void setCreationDateTime(LocalDateTime creationDateTime) {
		this.creationDateTime = creationDateTime;
	}

	public LocalDateTime getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(LocalDateTime endDateTime) {
		this.endDateTime = endDateTime;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public IntType getType() {
		return type;
	}

	public void setType(IntType type) {
		this.type = type;
	}

	public List<InterviewQuestion> getInterviewQuestions() {
		return interviewQuestions;
	}

	public void setInterviewQuestions(List<InterviewQuestion> interviewQuestions) {
		this.interviewQuestions = interviewQuestions;
	}

	public Double getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(Double maxScore) {
		this.maxScore = maxScore;
	}	
	
}
