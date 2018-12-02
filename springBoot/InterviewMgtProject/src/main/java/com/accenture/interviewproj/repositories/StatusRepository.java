package com.accenture.interviewproj.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accenture.interviewproj.entities.Status;

public interface StatusRepository extends JpaRepository<Status, Long> {

}
