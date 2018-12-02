package com.accenture.interviewproj.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.accenture.interviewproj.entities.Tracking;

public interface TrackingRepository extends JpaRepository<Tracking, Long> {
	
	@Query(value="SELECT * FROM TABLE_TRACKING WHERE JOB_ID = ? AND CANDIDATE_ID = ?", nativeQuery=true)
	public List<Tracking> findByCandidateIdAndJobId(Long jobId,Long candidateId);

}
