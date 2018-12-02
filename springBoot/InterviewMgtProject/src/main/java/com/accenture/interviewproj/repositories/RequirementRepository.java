package com.accenture.interviewproj.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accenture.interviewproj.entities.Requirement;

public interface RequirementRepository extends JpaRepository<Requirement, Long>{
	
	Requirement findByName(String name);

}
