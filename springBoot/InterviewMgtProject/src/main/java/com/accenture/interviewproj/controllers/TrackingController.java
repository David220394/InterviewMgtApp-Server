package com.accenture.interviewproj.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.interviewproj.dtos.TrackingDTO;
import com.accenture.interviewproj.entities.Tracking;
import com.accenture.interviewproj.exceptions.IdNotFoundException;
import com.accenture.interviewproj.services.TrackingService;

@RestController
@RequestMapping("/tracking")
@CrossOrigin(exposedHeaders="Access-Control-Allow-Origin",allowedHeaders="Content-Type")
public class TrackingController {

	@Autowired
	private TrackingService trackingService;
	
	/**
	 * 
	 * @param candidateId
	 * find by candidate id and user id 
	 */
	@GetMapping("/{jobId}/{candidateId}")
	public ResponseEntity<?> findByJobIdAndCandidateId(@PathVariable Long jobId, @PathVariable Long candidateId){
		try {
			List<Tracking> trackings = trackingService.findTrackingByJobIdAndCandidateId(jobId, candidateId);
			return ResponseEntity.ok(trackings);
		} catch (IdNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	/**
	 *
	 * @param trackingDto
	 * Insert the candidate tracking progress
	 */
	@PostMapping("/")
	public ResponseEntity<?> insertTracking(@RequestBody TrackingDTO trackingDto){
		trackingService.insertTracking(trackingDto);
			return ResponseEntity.ok("Inserted");
		
	}

}
