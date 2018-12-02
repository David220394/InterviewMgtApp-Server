package com.accenture.interviewproj.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="TABLE_SKILL")
public class Skill implements Serializable {
	
	private static final long serialVersionUID = 4124043367420129135L;

	@Id
	@Column(name="SKILL_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long skillId;
	
	@Column(name="SKILL_DESCRIPTION")
	private String description;
	
	@ManyToMany(mappedBy = "skills", fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Candidate> candidates;

	public Long getSkillId() {
		return skillId;
	}

	public void setSkillId(Long skillId) {
		this.skillId = skillId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Candidate> getCandidates() {
		return candidates;
	}

	public void setCandidates(Set<Candidate> candidates) {
		this.candidates = candidates;
	}

}
