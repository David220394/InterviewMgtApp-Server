package com.accenture.interviewproj.dtos;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.accenture.interviewproj.entities.CandidateExperience;
import com.accenture.interviewproj.entities.Education;
import com.accenture.interviewproj.entities.Interview;
import com.accenture.interviewproj.entities.Job;
import com.accenture.interviewproj.entities.JobCandidate;
import com.accenture.interviewproj.entities.Skill;
import com.accenture.interviewproj.entities.Tracking;
import com.accenture.interviewproj.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class DisplayCandidateDto {
	
	private Long candidateId;

	private String candidateName;

	private String candidateAddress;

	private Gender gender;

	private LocalDate dob;

	private LocalDateTime applicationDate;

	private String email;

	private Boolean completeApplication;

	private Boolean internalApplication;

	private Boolean rehire;

	private File candidateCv;

	private Boolean availability;

	private String coverLetter;

	private Long candidatePhone;

	private CandidateExperience candidateExperience;

	private Education education;

	private Set<Skill> skills;
	
	private String jobName;
	
	private String status;
	
	private Integer score;
	
	private Integer skillScore;

	public Long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getCandidateAddress() {
		return candidateAddress;
	}

	public void setCandidateAddress(String candidateAddress) {
		this.candidateAddress = candidateAddress;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public LocalDateTime getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(LocalDateTime applicationDate) {
		this.applicationDate = applicationDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getCompleteApplication() {
		return completeApplication;
	}

	public void setCompleteApplication(Boolean completeApplication) {
		this.completeApplication = completeApplication;
	}

	public Boolean getInternalApplication() {
		return internalApplication;
	}

	public void setInternalApplication(Boolean internalApplication) {
		this.internalApplication = internalApplication;
	}

	public Boolean getRehire() {
		return rehire;
	}

	public void setRehire(Boolean rehire) {
		this.rehire = rehire;
	}

	public File getCandidateCv() {
		return candidateCv;
	}

	public void setCandidateCv(File candidateCv) {
		this.candidateCv = candidateCv;
	}

	public Boolean getAvailability() {
		return availability;
	}

	public void setAvailability(Boolean availability) {
		this.availability = availability;
	}

	public String getCoverLetter() {
		return coverLetter;
	}

	public void setCoverLetter(String coverLetter) {
		this.coverLetter = coverLetter;
	}

	public Long getCandidatePhone() {
		return candidatePhone;
	}

	public void setCandidatePhone(Long candidatePhone) {
		this.candidatePhone = candidatePhone;
	}

	public CandidateExperience getCandidateExperience() {
		return candidateExperience;
	}

	public void setCandidateExperience(CandidateExperience candidateExperience) {
		this.candidateExperience = candidateExperience;
	}

	public Education getEducation() {
		return education;
	}

	public void setEducation(Education education) {
		this.education = education;
	}

	public Set<Skill> getSkills() {
		return skills;
	}

	public void setSkills(Set<Skill> skills) {
		this.skills = skills;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public Integer getSkillScore() {
		return skillScore;
	}

	public void setSkillScore(Integer skillScore) {
		this.skillScore = skillScore;
	}

	
	

}
