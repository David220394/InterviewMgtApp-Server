package com.accenture.interviewproj.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.accenture.interviewproj.entities.Interview;
import com.accenture.interviewproj.enums.IntType;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
	
	Interview findByLink(String link);
	
	@Query(value="SELECT * FROM TABLE_INTERVIEW WHERE COMPLETED=true AND CANDIDATE_ID=? AND JOB_ID=?",nativeQuery=true)
	List<Interview> findInterviewByCompletedAndCandidateIdAndJobId(long candidateId, long jobId);
	
	@Query(value="SELECT * FROM TABLE_INTERVIEW WHERE TYPE=? AND CANDIDATE_ID=? AND JOB_ID=? ORDER BY CREATION_DATE_TIME DESC",nativeQuery=true)
	List<Interview> findByTypeOrderByCreationDate(String type, long candidateId, long jobId);

}
