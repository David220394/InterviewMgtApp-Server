package com.accenture.interviewproj.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accenture.interviewproj.entities.Education;

public interface EducationRepository extends JpaRepository<Education, Long> {

}
