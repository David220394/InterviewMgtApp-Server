package com.accenture.interviewproj.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.accenture.interviewproj.dtos.JobDto;
import com.accenture.interviewproj.dtos.JobWithIdDto;
import com.accenture.interviewproj.dtos.QuestionDto;
import com.accenture.interviewproj.dtos.QuizDto;
import com.accenture.interviewproj.entities.AssessmentQuiz;
import com.accenture.interviewproj.entities.Employee;
import com.accenture.interviewproj.entities.Job;
import com.accenture.interviewproj.exceptions.JobNameAlreadyExistsException;
import com.accenture.interviewproj.exceptions.JobNotFoundException;
import com.accenture.interviewproj.services.AssessmentQuizService;
import com.accenture.interviewproj.services.EmployeeService;
import com.accenture.interviewproj.services.JobService;
import com.accenture.interviewproj.utilities.JobUtility;

@RestController
@RequestMapping("api/jobs")
@CrossOrigin
public class JobRestController {

	private final JobService jobService;
	
	private final EmployeeService employeeService;

	public JobRestController(JobService jobService,EmployeeService employeeService) {
		this.jobService = jobService;
		this.employeeService = employeeService;
	}

	/**
	 * 
	 * @param jobDto
	 * Creating a job
	 * checks if job already exist in database
	 */
	@PostMapping("/createJob")
	@Secured(value="ROLE_ADMIN")
	public ResponseEntity<?> createJob(@RequestBody JobDto jobDto) {
		try {
			Job job = jobService.insertJob(jobDto);
			return ResponseEntity.ok(job);
		} catch (JobNameAlreadyExistsException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Job name already exists");
		}
	}
	
	/**
	 * 
	 * @param file
	 * @param jobId
	 * @throws JobNameAlreadyExistsException
	 * Upload the assessment file 
	 */
	@PostMapping("/upload")
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, @RequestParam("jobId") Long jobId) throws JobNameAlreadyExistsException {
		
		Job updatedJob;
		try {
			updatedJob = jobService.updateJob(jobId, file);
			return ResponseEntity.ok(updatedJob);
		} catch (JobNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (IllegalStateException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (InvalidFormatException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PostMapping("/uploadQuiz")
	public ResponseEntity<?> updateQuiz(@RequestParam("jobId") Long jobId, @RequestParam("quiz") Long quiz){
		Job updatedJob;
		try {
			updatedJob = jobService.updateJobWithQuiz(jobId, quiz);
			return ResponseEntity.ok(updatedJob);
		} catch (JobNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
	}
	
	/**
	 * 
	 * @Get the list of all jobs 
	 */
	@GetMapping("/")
	public ResponseEntity<List<?>> getAllJobs() {
		return ResponseEntity.ok(jobService.findAllActiveJob());
	}
	
	/**
	 * List of jobs with id 
	 */
	@GetMapping("/getAll")
	public ResponseEntity<List<?>> getAllJobsWithId(){
		List <Job> jobs = jobService.findAllActiveJob();
		List<JobWithIdDto> jobDto = new ArrayList<>();
		
		for(Job job : jobs) {
			JobWithIdDto jobWithIdDto = JobUtility.convertJobWithIdDtoToJob(job);
			jobDto.add(jobWithIdDto);
		}
		return ResponseEntity.ok(jobDto);
	}
	
	/**
	 * 
	 * @param jobId
	 * Find a job by its id 
	 */
	@GetMapping("/{jobId}")
	public ResponseEntity<?> findJobByName(@PathVariable("jobId") Long jobId) {
		Job job = jobService.findByJobId(jobId);
		if (job != null) {
			return ResponseEntity.ok(job);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * 
	 * @param jobName
	 * Delete a job by its name
	 */
	@DeleteMapping("/{jobName}")
	public HttpEntity<String> deleteJob(@PathVariable("jobName") String jobName) {
		try {
			jobService.deleteJob(jobName);
			return ResponseEntity.ok("DELETED!!");
		} catch (JobNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@GetMapping("/quiz/{jobId}")
	public ResponseEntity<?> findQuiz(@PathVariable("jobId") long jobId){
		try {
			QuizDto quizDto= jobService.findQuiz(jobId);
			return ResponseEntity.ok(quizDto);
		} catch (EncryptedDocumentException | InvalidFormatException | JobNotFoundException | IOException e) {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@GetMapping("/activeJob/{eid}")
	public ResponseEntity<?> findActiveJobs(@PathVariable String eid)
	{
		try {
			List<Job> jobs = jobService.findActiveJobs(eid);
			List<JobWithIdDto> jobDto = new ArrayList<>();
			
			for(Job job : jobs) {
				JobWithIdDto jobWithIdDto = JobUtility.convertJobWithIdDtoToJob(job);
				jobDto.add(jobWithIdDto);
			}
			return ResponseEntity.ok(jobDto);
		}catch(JobNotFoundException e)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
}
