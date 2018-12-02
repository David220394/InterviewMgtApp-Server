package com.accenture.interviewproj.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accenture.interviewproj.entities.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

	public Question findByQuestion(String question);
}
