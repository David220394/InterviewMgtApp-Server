package com.accenture.interviewproj.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accenture.interviewproj.entities.TechnicalInterview;

public interface TechnicalInterviewRepository extends JpaRepository<TechnicalInterview, Long> {
	
	TechnicalInterview findByLink(String link);

}
