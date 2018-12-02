package com.accenture.interviewproj.services;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.interviewproj.dtos.TrackingDTO;
import com.accenture.interviewproj.entities.Tracking;
import com.accenture.interviewproj.enums.Track;
import com.accenture.interviewproj.exceptions.IdNotFoundException;
import com.accenture.interviewproj.repositories.CandidateRepository;
import com.accenture.interviewproj.repositories.EmployeeRepository;
import com.accenture.interviewproj.repositories.JobsRepository;
import com.accenture.interviewproj.repositories.TrackingRepository;

@Service
public class TrackingService {
	
	@Autowired
	private TrackingRepository trackingRepository;

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private CandidateRepository candidateRepository;

	@Autowired
	private JobsRepository jobRepository;

	public List<Tracking> findTrackingByJobIdAndCandidateId(Long jobId,Long candidateId) throws IdNotFoundException {
		List<Tracking> trackings = trackingRepository.findByCandidateIdAndJobId(jobId, candidateId);
		if(!trackings.isEmpty()) {
			return trackings;
		}else {
			throw new IdNotFoundException("Candidate ID not found");
		}
		
	}
	
	/**
	 * 
	 * @param trackingDto
	 * Insert the tracking progress of the candidate
	 */
	public void insertTracking(TrackingDTO trackingDto) {
		Tracking tracking = new Tracking();
		tracking.setComment(trackingDto.getComment());
		tracking.setTrackingType(Track.valueOf(trackingDto.getTrackingType()));
		tracking.setCandidate(candidateRepository.findById(trackingDto.getCandidateId()).get());
		tracking.setEmployee(employeeRepository.findById(trackingDto.getEmployeeId()).get());
		tracking.setJob(jobRepository.findByJobId(trackingDto.getJobId()));
		trackingRepository.save(tracking);
	}
	
	
}
