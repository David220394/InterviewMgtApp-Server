package com.accenture.interviewproj.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="TABLE_CANDIDATE_SKILL")
public class CandidateSkillScore implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CANDIDATE_SKILL_SCORE_ID")
	private long candidateSkillScoreId;
	
	@Column(name="SKILL_SCORE")
	private int skillScore;
	
	@ManyToOne
	@JoinColumn(name="JOB_ID")
	private Job job;
	
	@ManyToOne
	@JoinColumn(name="CANDIDATE_ID")
	private Candidate candidate;

	public long getCandidateSkillScoreId() {
		return candidateSkillScoreId;
	}

	public void setCandidateSkillScoreId(long candidateSkillScoreId) {
		this.candidateSkillScoreId = candidateSkillScoreId;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public int getSkillScore() {
		return skillScore;
	}

	public void setSkillScore(int skillScore) {
		this.skillScore = skillScore;
	}
	
	
	@PrePersist
	public void initSkillScore() {
		double skillMatch = 0;
		int maxReq = 0;
		maxReq = this.job.getRequirements().size();
		for(Skill skill : this.candidate.getSkills()) {
			for(Requirement requirement : this.job.getRequirements()) {
				if(skill.getDescription().contains(requirement.getName())) {
					skillMatch++;
				}
			}
		}
		this.skillScore = (int)((skillMatch/maxReq) * 100);
	}

}
