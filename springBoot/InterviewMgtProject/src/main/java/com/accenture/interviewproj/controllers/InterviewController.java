package com.accenture.interviewproj.controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.interviewproj.dtos.AfterAssessmentDto;
import com.accenture.interviewproj.dtos.AfterInterviewDto;
import com.accenture.interviewproj.dtos.InterviewDto;
import com.accenture.interviewproj.dtos.QuizDto;
import com.accenture.interviewproj.entities.Interview;
import com.accenture.interviewproj.exceptions.ExpiredLinkException;
import com.accenture.interviewproj.exceptions.IdNotFoundException;
import com.accenture.interviewproj.exceptions.TypeNotFoundException;
import com.accenture.interviewproj.services.AssessmentQuizService;
import com.accenture.interviewproj.services.InterviewService;

@RestController
@RequestMapping("/interview")
@CrossOrigin
public class InterviewController {
	
	@Autowired
	private InterviewService interviewService;
	
	@Autowired
	private AssessmentQuizService assessmentQuizService;
	
	@PostMapping("/")
	public ResponseEntity<?> createInterview(@RequestBody InterviewDto interviewDto) {
		try {
			return ResponseEntity.ok(interviewService.createInterview(interviewDto));
		} catch (TypeNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@GetMapping("/{link}")
	public ResponseEntity<?> accessInterviewFormPage(@PathVariable String link){
		try {
			Interview interview = interviewService.findByLink(link);
			if(interview.getCreationDateTime() == null) {
				//Set creation date and completed date and send the interview object in response.
				return ResponseEntity.ok(interviewService.updateTime(interview));
			}
			//Check if the link is not expired or if the interview have already been completed.
			else if(interview.getEndDateTime().isAfter(LocalDateTime.now()) && !interview.getCompleted()) {
				return ResponseEntity.ok(interview);
			}
		} catch (ExpiredLinkException e) {
		}
		return ResponseEntity.status(HttpStatus.GONE).body("Link Expired");
	}
	
	@PostMapping("/afterinterview/")
	public ResponseEntity<?> updateInterview(@RequestBody AfterInterviewDto afterInterviewDto){
		try { 
			return ResponseEntity.ok(interviewService.updateInterview(afterInterviewDto));
		} catch (TypeNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());		
			}
	}
	
	@GetMapping("/{jobId}/{candidateId}")
	public ResponseEntity<?> getCompletedInterview(@PathVariable Long jobId,@PathVariable Long candidateId){
		try {
			return ResponseEntity.ok(interviewService.findByCompletedAndCandidateIdAndJobId(candidateId, jobId));
		} catch (IdNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());		
		}
	}
	
	@GetMapping("/score/{jobId}/{candidateId}")
	public ResponseEntity<?> getScores(@PathVariable Long jobId,@PathVariable Long candidateId){
		try {
			return ResponseEntity.ok(interviewService.findScore(candidateId, jobId));
		} catch (IdNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());	
		}
	}
	
	@PostMapping("/createQuiz/")
	public ResponseEntity<?> saveAssessmentQuiz(@RequestBody QuizDto quizDto){
		return ResponseEntity.ok(assessmentQuizService.insertAssessmentQuiz(quizDto));
	}
	
	@PostMapping("/updateQuiz/")
	public ResponseEntity<?> updateAssessmentQuiz(@RequestBody QuizDto quizDto){
		return ResponseEntity.ok(assessmentQuizService.updateAssessmentQuiz(quizDto));
	}
	
	@GetMapping("/findAllQuiz/")
	public ResponseEntity<?> getAllAssessmentQuiz(){
		return ResponseEntity.ok(assessmentQuizService.getAllAssessmentQuizs());
	}
	
	@GetMapping("/findQuiz/{id}")
	public ResponseEntity<?> getAllAssessmentQuiz(@PathVariable("id") long quizId){
		return ResponseEntity.ok(assessmentQuizService.findQuizById(quizId));
	}
	
	@PostMapping("/assessmentInterview/")
	public ResponseEntity<?> calculateAssessmentScore(@RequestBody AfterAssessmentDto afterAssessmentDto){
		return ResponseEntity.ok(assessmentQuizService.saveAssessmentScore(afterAssessmentDto));
	}

}
