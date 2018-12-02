package com.accenture.interviewproj.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accenture.interviewproj.entities.InterviewQuestion;

public interface InterviewQuestionRepository extends JpaRepository<InterviewQuestion, Long> {

}
