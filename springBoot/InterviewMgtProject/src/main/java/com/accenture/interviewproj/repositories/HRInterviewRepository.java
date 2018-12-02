package com.accenture.interviewproj.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accenture.interviewproj.entities.HRInterview;

public interface HRInterviewRepository extends JpaRepository<HRInterview, Long> {
	
	HRInterview findByLink(String link);

}
