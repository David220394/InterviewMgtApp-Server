package com.accenture.interviewproj.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accenture.interviewproj.entities.AssessmentInterview;

public interface AssessmentInterviewRepository extends JpaRepository<AssessmentInterview, Long> {
	
	AssessmentInterview findByLink(String link);

}
