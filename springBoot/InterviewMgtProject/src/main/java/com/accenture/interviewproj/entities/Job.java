package com.accenture.interviewproj.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TABLE_JOB")
public class Job implements Serializable {
	private static final long serialVersionUID = -2450298674198837000L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "JOB_ID")
	private Long jobId;

	@Column(name = "JOB_NAME", unique = true)
	private String jobName;

	@Column(name = "POSITION")
	private String position;

	@Column(name = "LOCATION")
	private String location;

	@Column(name = "CLOSING_DATE", nullable = false)
	private LocalDateTime closingDate;

	@Column(name = "CREATION_DATE", nullable = false)
	private LocalDateTime creationDate;

	@ManyToOne
	@JoinColumn(name = "ASSESSMENT_QUIZ_ID")
	private AssessmentQuiz assessmentQuiz;

	@Column(name = "ACTIVE_JOB")
	private Boolean activeJob;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "TABLE_JOB_REQUIREMENT", 
	joinColumns = { @JoinColumn(name = "JOB_ID")},
	inverseJoinColumns = { @JoinColumn(name = "REQUIREMENT_ID")})
	private Set<Requirement> requirements;

	@ElementCollection
	@JsonIgnore
	private List<String> assignTo = new ArrayList<>();

	@ManyToMany(mappedBy = "jobs", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIgnore
	private List<Employee> employee;
	
	@OneToMany(mappedBy = "job")
	@JsonIgnore
	private Set<Interview> interviews;
	
	@OneToMany(mappedBy = "job")

	private List<JobCandidate> jobCandidates;
	
	@OneToMany(mappedBy = "job")
	@JsonIgnore
	private List<CandidateSkillScore> candidateSkillScores;
	
	@OneToMany(mappedBy = "job")
	@JsonIgnore
	private Set<Tracking> trakings;

	
	public Boolean getActiveJob() {
		return activeJob;
	}

	public void setActiveJob(Boolean activeJob) {
		this.activeJob = activeJob;
	}

	public Set<Tracking> getTrakings() {
		return trakings;
	}

	public void setTrakings(Set<Tracking> trakings) {
		this.trakings = trakings;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public List<Employee> getEmployee() {
		return employee;
	}

	public void setEmployee(List<Employee> employee) {
		this.employee = employee;
	}

	public Set<Requirement> getRequirements() {
		return requirements;
	}

	public void setRequirements(Set<Requirement> requirements) {
		this.requirements = requirements;
	}

	public Set<Interview> getInterviews() {
		return interviews;
	}

	public void setInterviews(Set<Interview> interviews) {
		this.interviews = interviews;
	}


	public List<JobCandidate> getJobCandidates() {
		return jobCandidates;
	}

	public void setJobCandidates(List<JobCandidate> jobCandidates) {
		this.jobCandidates = jobCandidates;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public LocalDateTime getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(LocalDateTime closingDate) {
		this.closingDate = closingDate;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}
	
	public AssessmentQuiz getAssessmentQuiz() {
		return assessmentQuiz;
	}

	public void setAssessmentQuiz(AssessmentQuiz assessmentQuiz) {
		this.assessmentQuiz = assessmentQuiz;
	}

	public List<String> getAssignTo() {
		return assignTo;
	}

	public void setAssignTo(List<String> assignTo) {
		this.assignTo = assignTo;
	}

	public List<CandidateSkillScore> getCandidateSkillScores() {
		return candidateSkillScores;
	}

	public void setCandidateSkillScores(List<CandidateSkillScore> candidateSkillScores) {
		this.candidateSkillScores = candidateSkillScores;
	}

	@PrePersist
	public void initCreatedDate() {
		this.creationDate = LocalDateTime.now();
		if(this.closingDate == null) {
			this.closingDate = LocalDateTime.now().plusMonths(2);
		}
	}


	
	

}
