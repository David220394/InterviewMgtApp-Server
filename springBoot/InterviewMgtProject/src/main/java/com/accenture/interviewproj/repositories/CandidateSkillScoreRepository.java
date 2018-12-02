package com.accenture.interviewproj.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.accenture.interviewproj.entities.Candidate;
import com.accenture.interviewproj.entities.CandidateSkillScore;
import com.accenture.interviewproj.entities.Job;

public interface CandidateSkillScoreRepository extends JpaRepository<CandidateSkillScore, Long> {
	
	List<CandidateSkillScore> findByCandidate(Candidate candidate);
	
	@Query(value="SELECT SKILL_SCORE FROM TABLE_CANDIDATE_SKILL WHERE JOB_ID=? AND CANDIDATE_ID=?",nativeQuery=true)
	Integer findCandidateSkillScoreByJobIdAndCandidateId(Long jobId, Long cid);
	

}
