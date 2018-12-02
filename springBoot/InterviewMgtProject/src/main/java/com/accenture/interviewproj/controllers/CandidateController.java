package com.accenture.interviewproj.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.accenture.interviewproj.dtos.DisplayCandidateDto;
import com.accenture.interviewproj.entities.Candidate;
import com.accenture.interviewproj.exceptions.IdNotFoundException;
import com.accenture.interviewproj.exceptions.JobNameAlreadyExistsException;
import com.accenture.interviewproj.services.CandidateService;

@CrossOrigin
@RestController
@RequestMapping("/candidate")
public class CandidateController {
	
	private static String uploadCVFolder = System.getProperty("user.dir")
			+ "/src/main/resources/CandidateCv";
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
    JobLauncher jobLauncher;
 
    @Autowired
    Job job;
 
    @RequestMapping("/launchjob")
    public String handle() throws Exception {
 
        Logger logger = LoggerFactory.getLogger(this.getClass());
        try {
        	JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(job, jobParameters);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
 
        return "Done!";
    }

	/**
	 * 
	 * @param jobId
	 * finding a candidate by its jobId
	 */
	@GetMapping("/{jobId}")
	public ResponseEntity<?> findCandidateByJodId(@PathVariable Long jobId){
		List<DisplayCandidateDto> candidates = candidateService.findCandidateByJobId(jobId);
		if(candidates != null) {
		return ResponseEntity.ok(candidates);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job Id not found");
		}
	}
	
	/**
	 * 
	 * @param jobId
	 * finding a candidate by its jobId
	 */
	@GetMapping("/suggest/{jobId}")
	public ResponseEntity<?> findSuggestedCandidateByJodId(@PathVariable Long jobId){
		List<DisplayCandidateDto> candidates = candidateService.findSuggestedCandidateByJobId(jobId);
		if(candidates != null) {
		return ResponseEntity.ok(candidates);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job Id not found");
		}
	}
	
	/**
	 * 
	 * @param jobId
	 * @param cid
	 * finding a candidate by its jobId and candidate id
	 */
	@GetMapping("/{jobId}/{cid}")
	public ResponseEntity<?> findCandidateByJobIdAndId(@PathVariable Long jobId,@PathVariable Long cid){
		try {
			return ResponseEntity.ok(candidateService.findCandidateByJobIdAndCandidateId(jobId,cid));
		} catch (IdNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param cid
	 * finding a candidate by its candidate id
	 */
	@GetMapping("candidatePage/{cid}")
	public ResponseEntity<?> findCandidateById(@PathVariable Long cid){
		try {
			return ResponseEntity.ok(candidateService.findCandidateByCandidateId(cid));
		} catch (IdNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	/**
	 * Add job to a candidate
	 * @param jobId
	 * @param cid
	 * @return
	 */
	@GetMapping("/addJob/{jobId}/{cid}")
	public ResponseEntity<?> addJob(@PathVariable Long jobId,@PathVariable Long cid){
		try {
			return ResponseEntity.ok(candidateService.addJobToCandidate(cid, jobId));
		} catch (JobNameAlreadyExistsException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
	
	
	
	@PostMapping("/")
	public ResponseEntity<?> createCandidate(@RequestParam MultipartFile file){
		return ResponseEntity.ok(candidateService.createCandidate(file));
	}
	
	/**
	 * Find All candidate
	 * @return
	 */
	@GetMapping("/")
	public ResponseEntity<?> findAll(){
		return ResponseEntity.ok(candidateService.findAll());
	}
	
	@GetMapping("/updateStatus/{jobId}/{cid}/{is_change}/{oldStatus}")
	public ResponseEntity<?> updateStatus(@PathVariable Long jobId,@PathVariable Long cid,@PathVariable String is_change,@PathVariable String oldStatus){
		return ResponseEntity.ok(candidateService.updateCandidateStatus(cid, jobId, is_change,oldStatus));
	}
	
	/**
	 * Method to send a candidate CV  
	 * 
	 */
	@RequestMapping(value = "createPdf/{cid}", method = RequestMethod.GET, produces = "application/pdf")
	public ResponseEntity<?> getPdf(@PathVariable Long cid,HttpServletResponse response) {

	    response.setContentType("application/pdf");//Set Response type
	    Candidate candidate;
	    byte[] contents =null;
		try {
			candidate = candidateService.findCandidateByCandidateId(cid);//Obtain the Candidate object
			Path path = Paths.get(uploadCVFolder, candidate.getCandidateCv()); //Get the full path of the CV
			contents = Files.readAllBytes(path);//Save the CV in an array of bytes
		} catch (IdNotFoundException | IOException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.parseMediaType("application/pdf"));
	    return new ResponseEntity<>(contents, headers, HttpStatus.OK);        
	}

}
