package com.accenture.interviewproj.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.accenture.interviewproj.entities.Candidate;

public interface CandidateRepository extends JpaRepository<Candidate, Long>{
	
	@Query(value="SELECT c.*\r\n" + 
			"FROM (table_candidate c \r\n" + 
			"inner join \r\n" + 
			"table_job_candidate jc\r\n" + 
			"on c.candidate_id = jc.candidate_id) inner join \r\n" + 
			"table_candidate_skill cs\r\n" + 
			"on jc.job_id=cs.job_id and jc.candidate_id=cs.candidate_id\r\n" + 
			"where jc.job_id=?\r\n" + 
			"order by cs.skill_score desc", nativeQuery=true)
	List<Candidate> findByJobId(Long jobId);
	
	@Query(value="SELECT c.*\r\n" + 
			"FROM interviewmgt.table_candidate c \r\n" + 
			" inner join \r\n" + 
			"interviewmgt.table_candidate_skill cs\r\n" + 
			"on c.candidate_id=cs.candidate_id \r\n" + 
			"where cs.job_id=?\r\n" + 
			"order by cs.skill_score desc",nativeQuery=true)
	List<Candidate> findSuggestedCandidateByJobId(Long jobId);
	
	@Query(value="SELECT tc.* FROM TABLE_CANDIDATE tc LEFT JOIN TABLE_JOB_CANDIDATE tjc ON tc.CANDIDATE_ID = tjc.CANDIDATE_ID WHERE tjc.JOB_ID=? AND tc.CANDIDATE_ID=?", nativeQuery=true)
	Candidate findByJobIdAndCandidateId(Long jobId, Long cid);
	
	@Query(value="SELECT * FROM TABLE_CANDIDATE tc LEFT JOIN TABLE_JOB_CANDIDATE tjc ON tc.CANDIDATE_ID = tjc.CANDIDATE_ID WHERE tjc.JOB_ID=? AND tc.CANDIDATE_NAME=?", nativeQuery=true)
	List<Candidate> findByJobIdAndCandidateName(Long jobId, String candidateName);

	Candidate findByCandidateName(String candidateName);
	
}
