package com.accenture.interviewproj.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accenture.interviewproj.entities.AssessmentQuiz;

public interface AssessmentQuizRepositorty extends JpaRepository<AssessmentQuiz, Long> {
	
	AssessmentQuiz findByQuizName(String name);

}
