package com.accenture.interviewproj.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accenture.interviewproj.entities.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long> {

	Skill findByDescription(String description);
}
