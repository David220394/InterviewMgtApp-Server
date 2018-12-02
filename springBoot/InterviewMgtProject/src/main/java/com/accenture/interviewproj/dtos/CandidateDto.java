package com.accenture.interviewproj.dtos;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.accenture.interviewproj.entities.CandidateExperience;
import com.accenture.interviewproj.entities.Education;
import com.accenture.interviewproj.entities.Interview;
import com.accenture.interviewproj.entities.Job;
import com.accenture.interviewproj.entities.Skill;
import com.accenture.interviewproj.entities.Status;
import com.accenture.interviewproj.entities.Tracking;
import com.accenture.interviewproj.enums.Gender;

public class CandidateDto {

	private String candidateName;

	private String candidateAddress;

	private Gender gender;

	private LocalDate dob;

	private LocalDateTime applicationDate;

	private String email;

	private Boolean completeApplication;

	private Boolean internalApplication;

	private Boolean rehire;

	private String candidateCv;

	private String status;

	private Boolean availability;

	private Long candidatePhone;
	
	private String specialty;
	
	private String lastJobRole;
	
	private String employer;
	
	private String institution;
	
	private String programStudy;
	
	private Double grade;

	private String jobName;
	
	private List<String> skills;

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

	
	public String getCandidateCv() {
		return candidateCv;
	}

	public void setCandidateCv(String candidateCv) {
		this.candidateCv = candidateCv;
	}

	public Boolean getAvailability() {
		return availability;
	}

	public void setAvailability(Boolean availability) {
		this.availability = availability;
	}

	public Long getCandidatePhone() {
		return candidatePhone;
	}

	public void setCandidatePhone(Long candidatePhone) {
		this.candidatePhone = candidatePhone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getLastJobRole() {
		return lastJobRole;
	}

	public void setLastJobRole(String lastJobRole) {
		this.lastJobRole = lastJobRole;
	}

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public String getProgramStudy() {
		return programStudy;
	}

	public void setProgramStudy(String programStudy) {
		this.programStudy = programStudy;
	}

	public Double getGrade() {
		return grade;
	}

	public void setGrade(Double grade) {
		this.grade = grade;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public List<String> getSkills() {
		return skills;
	}

	public void setSkills(List<String> skills) {
		this.skills = skills;
	}


	


}
