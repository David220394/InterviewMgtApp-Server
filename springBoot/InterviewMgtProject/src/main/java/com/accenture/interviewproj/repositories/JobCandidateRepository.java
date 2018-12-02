package com.accenture.interviewproj.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.accenture.interviewproj.entities.Candidate;
import com.accenture.interviewproj.entities.Job;
import com.accenture.interviewproj.entities.JobCandidate;

public interface JobCandidateRepository extends JpaRepository<JobCandidate, Long> {
	
	@Query(value="SELECT s.STATUS_NAME FROM TABLE_STATUS s INNER JOIN TABLE_JOB_CANDIDATE jc ON jc.JOB_CANDIDATE_ID = s.JOB_CANDIDATE_ID WHERE jc.CANDIDATE_ID=? AND jc.JOB_ID=? ORDER BY s.status_change_date DESC", nativeQuery=true)
	List<String> findStatusByCandidateIdAndJobId(Long cid, Long jobId);
	
	@Query(value="SELECT candidate_score FROM TABLE_JOB_CANDIDATE jc WHERE jc.CANDIDATE_ID=? AND jc.JOB_ID=?", nativeQuery=true)
	List<Integer> findScoreByCandidateIdAndJobId(Long cid, Long jobId);
	
	@Query(value="SELECT j.job_name FROM TABLE_JOB_CANDIDATE jc INNER JOIN TABLE_JOB j ON jc.job_id=j.job_id  WHERE jc.CANDIDATE_ID=? AND jc.JOB_ID=?", nativeQuery=true)
	String findJobNameByCandidateIdAndJobId(Long cid, Long jobId);
	
	JobCandidate findByCandidateAndJob(Candidate candidate, Job job);

}
