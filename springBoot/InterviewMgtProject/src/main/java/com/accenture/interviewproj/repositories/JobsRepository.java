package com.accenture.interviewproj.repositories;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.accenture.interviewproj.entities.Job;

public interface JobsRepository extends JpaRepository<Job, Long>{
	
	public Job findByJobId(Long jobId);
	
	public Job findByJobName(String jobName);
	
	@Query(value="SELECT JOB_JOB_ID FROM TABLE_JOB_EMPLOYEE WHERE EMPLOYEE_EMPLOYEE_ID=?", nativeQuery=true)
	List<BigInteger> findActiveJob (String eid);
	
	List<Job> findByActiveJob(boolean activeJob);
}
