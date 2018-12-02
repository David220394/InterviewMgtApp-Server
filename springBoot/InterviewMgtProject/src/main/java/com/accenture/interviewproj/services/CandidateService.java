package com.accenture.interviewproj.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.accenture.interviewproj.dtos.CandidateDto;
import com.accenture.interviewproj.dtos.DisplayCandidateDto;
import com.accenture.interviewproj.dtos.QuestionDto;
import com.accenture.interviewproj.dtos.QuizDto;
import com.accenture.interviewproj.entities.AssessmentQuiz;
import com.accenture.interviewproj.entities.Candidate;
import com.accenture.interviewproj.entities.CandidateExperience;
import com.accenture.interviewproj.entities.CandidateSkillScore;
import com.accenture.interviewproj.entities.Education;
import com.accenture.interviewproj.entities.Job;
import com.accenture.interviewproj.entities.JobCandidate;
import com.accenture.interviewproj.entities.Requirement;
import com.accenture.interviewproj.entities.Skill;
import com.accenture.interviewproj.entities.Status;
import com.accenture.interviewproj.enums.Gender;
import com.accenture.interviewproj.exceptions.IdNotFoundException;
import com.accenture.interviewproj.exceptions.JobNameAlreadyExistsException;
import com.accenture.interviewproj.repositories.CandidateExperienceRepository;
import com.accenture.interviewproj.repositories.CandidateRepository;
import com.accenture.interviewproj.repositories.CandidateSkillScoreRepository;
import com.accenture.interviewproj.repositories.EducationRepository;
import com.accenture.interviewproj.repositories.JobCandidateRepository;
import com.accenture.interviewproj.repositories.JobsRepository;
import com.accenture.interviewproj.repositories.RequirementRepository;
import com.accenture.interviewproj.repositories.SkillRepository;
import com.accenture.interviewproj.repositories.StatusRepository;
import com.accenture.interviewproj.utilities.CandidateUtility;
import com.accenture.interviewproj.utilities.JobUtility;

@Service
public class CandidateService {
	
	@Autowired
	private CandidateRepository candidateRepository;
	
	@Autowired
	private SkillRepository skillRepository;
	
	@Autowired
	private CandidateExperienceRepository candidateExperienceRepository;
	
	@Autowired
	private JobsRepository jobRepository;
	
	@Autowired
	private StatusRepository statusRepository;
	
	@Autowired
	private EducationRepository educationRepository;
	
	@Autowired
	private RequirementRepository requirementRepository;
	
	@Autowired
	private JobCandidateRepository jobCandidateRepository;
	
	@Autowired
	private AssessmentQuizService assessmentQuizService;
	
	@Autowired
	private CandidateSkillScoreRepository candidateSkillScoreRepository;
	

	public Candidate createCandidate(MultipartFile candidateFile) {
		try {
			Workbook workbook = WorkbookFactory.create(candidateFile.getInputStream());
			Sheet sheet = workbook.getSheetAt(0);
			DataFormatter dataFormatter = new DataFormatter();
			Iterator<Row> rowIterator = sheet.rowIterator();
			if(!rowIterator.hasNext()) {
				return null;
			}
			rowIterator.next();
			while(rowIterator.hasNext()) {
				Row row = rowIterator.next();
				CandidateDto candidateDto = CandidateUtility.getCandidateFromExcel(row);
				createCandidateFromDto(candidateDto);
			}
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) { }
		
		return null;
	}
	
	public Candidate createCandidateFromDto(CandidateDto candidateDto) {
		Candidate candidate = null;
		Job job =null;
		if(candidateRepository.findByCandidateName(candidateDto.getCandidateName()) == null) {//Check if candidate already exist
			Set<Skill> skills = new HashSet<>();
			for (String skillValue : candidateDto.getSkills()) {//Stored each skill if does not already exist 
				Skill skill = skillRepository.findByDescription(skillValue);
				if(skill == null) {
					skill = new Skill();
					skill.setDescription(skillValue);
					skillRepository.save(skill);
				}
				skills.add(skill);
			}
			
			candidate = CandidateUtility.convertCandidateDtoToCandidate(candidateDto);
			candidate.setSkills(skills);
			candidateRepository.save(candidate);// Save Candidate
			if(candidateDto.getJobName() != null) {// Check if Job Name exist
				job = jobRepository.findByJobName(candidateDto.getJobName());
				
				JobCandidate jobCandidate = new JobCandidate(); //Link Candidate to Job
				jobCandidate.setCandidate(candidate);
				jobCandidate.setJob(job);
				jobCandidate.setScore(0);
				jobCandidateRepository.save(jobCandidate);// Save CandidateJob
				
				Status status = new Status();
				status.setStatusName(candidateDto.getStatus());
				status.setCandidate(jobCandidate);
				statusRepository.save(status);// Save CandidateJob Status
			}
			Education education = new Education();
			education.setInstitutionName(candidateDto.getInstitution());
			education.setProgramStudy(candidateDto.getProgramStudy());
			education.setGrade(candidateDto.getGrade());
			education.setCandidate(candidate);
			educationRepository.save(education);//Save Education 
			
			CandidateExperience candidateExperience = new CandidateExperience();
			candidateExperience.setExperienceName(candidateDto.getLastJobRole());
			candidateExperience.setSpecialty(candidateDto.getSpecialty());
			candidateExperience.setLocation(candidateDto.getEmployer());
			candidateExperience.setCandidate(candidate);
			candidateExperienceRepository.save(candidateExperience);//Save Candidate Exp
			
			/**
			 * For All job, Calculate and save score obtained by comparing candidate
			 * skill the job requirement
			 */
			List <Job> jobs = jobRepository.findAll();
			for (Job job2 : jobs) { 
				CandidateSkillScore candidateSkillScore = new CandidateSkillScore();
				candidateSkillScore.setCandidate(candidate);
				candidateSkillScore.setJob(job2);
				candidateSkillScoreRepository.save(candidateSkillScore);
			}
		}
		return candidate;
	}
	
	
	
	/**
	 * 
	 * @param cid
	 * @throws IdNotFoundException
	 * find a candidate by candidate id
	 */
	public Candidate findCandidateByCandidateId(Long cid) throws IdNotFoundException {
		if( candidateRepository.findById(cid).isPresent()) {
		return  candidateRepository.findById(cid).get();
		}else {
			throw new IdNotFoundException("Candidate ID not Found For this Job");
		}
	}
	
	/**
	 * 
	 * Find all candidate
	 */
	public List<Candidate> findAll(){
		
		return candidateRepository.findAll();
	}
	

	/**
	 * 
	 * @param jobId
	 * @param cid
	 * @throws IdNotFoundException
	 * find a candidate by job id and candidate id
	 */
	public DisplayCandidateDto findCandidateByJobIdAndCandidateId(Long jobId, Long cid) throws IdNotFoundException {
		Candidate candidate = candidateRepository.findByJobIdAndCandidateId(jobId,cid);
		if(candidate != null) {
				DisplayCandidateDto candidateDto = CandidateUtility.convertCandidateToDto(candidate);
				candidateDto.setStatus(jobCandidateRepository.findStatusByCandidateIdAndJobId(cid, jobId).get(0));
				candidateDto.setScore(jobCandidateRepository.findScoreByCandidateIdAndJobId(cid, jobId).get(0));
				candidateDto.setJobName(jobCandidateRepository.findJobNameByCandidateIdAndJobId(cid, jobId));
				candidateDto.setSkillScore(candidateSkillScoreRepository.findCandidateSkillScoreByJobIdAndCandidateId(jobId, cid));
		return candidateDto;
		}else {
			throw new IdNotFoundException("Candidate ID not Found For this Job");
		}
	}
	
	/**
	 * 
	 * @param jobId
	 * Find a candidate by job id
	 */
	public List<DisplayCandidateDto> findCandidateByJobId(Long jobId){
		List<DisplayCandidateDto> candidateDtos = new ArrayList<>();
		List<Candidate> candidates = candidateRepository.findByJobId(jobId);
		for (Candidate candidate : candidates) {
			DisplayCandidateDto candidateDto = CandidateUtility.convertCandidateToDto(candidate);
			candidateDto.setStatus(jobCandidateRepository.findStatusByCandidateIdAndJobId(candidate.getCandidateId(), jobId).get(0));
			candidateDto.setScore(jobCandidateRepository.findScoreByCandidateIdAndJobId(candidate.getCandidateId(), jobId).get(0));
			candidateDto.setJobName(jobCandidateRepository.findJobNameByCandidateIdAndJobId(candidate.getCandidateId(), jobId));
			candidateDto.setSkillScore(candidateSkillScoreRepository.findCandidateSkillScoreByJobIdAndCandidateId(jobId, candidate.getCandidateId()));
			candidateDtos.add(candidateDto);
		}
		return candidateDtos;
	}
	
	/**
	 * 
	 * @param jobId
	 * Find suggested candidate for a specific job
	 */
	public List<DisplayCandidateDto> findSuggestedCandidateByJobId(Long jobId){
		List<DisplayCandidateDto> candidateDtos = new ArrayList<>();
		List<Candidate> suggested = candidateRepository.findSuggestedCandidateByJobId(jobId);
		List<Candidate> candidates = candidateRepository.findByJobId(jobId);
		suggested.removeAll(candidates);
		for (Candidate candidate : suggested) {
			DisplayCandidateDto candidateDto = CandidateUtility.convertCandidateToDto(candidate);
			candidateDto.setJobName(jobCandidateRepository.findJobNameByCandidateIdAndJobId(candidate.getCandidateId(), jobId));
			candidateDto.setSkillScore(candidateSkillScoreRepository.findCandidateSkillScoreByJobIdAndCandidateId(jobId, candidate.getCandidateId()));
			candidateDtos.add(candidateDto);
		}
		return candidateDtos;
	}
	
	
	public Candidate addJobToCandidate(Long cid, Long jobId) throws JobNameAlreadyExistsException {
		if(candidateRepository.findByJobIdAndCandidateId(jobId, cid) == null) {	
		Job job = jobRepository.findByJobId(jobId);
		Candidate candidate = candidateRepository.findById(cid).get();
		JobCandidate jobCandidate = new JobCandidate();
		jobCandidate.setCandidate(candidate);
		jobCandidate.setJob(job);
		jobCandidate.setScore(0);
		jobCandidateRepository.save(jobCandidate);
		
		Status status = new Status();
		status.setStatusName("Applied");
		status.setCandidate(jobCandidate);
		statusRepository.save(status);
		
		return candidate;
		}else {
			throw new JobNameAlreadyExistsException("This candidate is already assigned to the job");
		}
	}
	
	
	/**
	 * Change status of candidate
	 */
	public Status updateCandidateStatus(long candidateId, long jobId, String is_change, String oldStatus) {
		List<String> status = new ArrayList<>();
		Status savedStatus=null;
		status.add("Applied");
		status.add("Scheduled Interview");
		status.add("Interview In progress");
		status.add("Completed");
		status.add("Rejected");
		
		if(!is_change.equals("NOTHING")) {
			Candidate candidate = candidateRepository.findById(candidateId).get();
			Job job = jobRepository.findByJobId(jobId);
			JobCandidate jobCandidate = jobCandidateRepository.findByCandidateAndJob(candidate, job);
			String newStatus;
			if(is_change.equals("CHANGE")) {
				newStatus = status.get(status.indexOf(oldStatus)+1);
			}else {
				newStatus = "Rejected";
			}
			savedStatus = new Status();
			savedStatus.setStatusName(newStatus);
			savedStatus.setCandidate(jobCandidate);
			return statusRepository.save(savedStatus);
		}
		return savedStatus;
	}

}
