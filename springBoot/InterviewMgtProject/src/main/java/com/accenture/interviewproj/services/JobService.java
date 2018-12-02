package com.accenture.interviewproj.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.accenture.interviewproj.dtos.JobDto;
import com.accenture.interviewproj.dtos.QuestionDto;
import com.accenture.interviewproj.dtos.QuizDto;
import com.accenture.interviewproj.entities.AssessmentQuiz;
import com.accenture.interviewproj.entities.Employee;
import com.accenture.interviewproj.entities.Job;
import com.accenture.interviewproj.entities.QuizQuestion;
import com.accenture.interviewproj.entities.Requirement;
import com.accenture.interviewproj.exceptions.JobNameAlreadyExistsException;
import com.accenture.interviewproj.exceptions.JobNotFoundException;
import com.accenture.interviewproj.repositories.AssessmentQuizRepositorty;
import com.accenture.interviewproj.repositories.EmployeeRepository;
import com.accenture.interviewproj.repositories.JobsRepository;
import com.accenture.interviewproj.repositories.QuizQuestionRepository;
import com.accenture.interviewproj.repositories.RequirementRepository;
import com.accenture.interviewproj.utilities.JobUtility;

@Service
public class JobService {

	private final JobsRepository jobRepository;

	private final EmployeeRepository employeeRepository;

	private final RequirementRepository requirementRepository;

	private final AssessmentQuizService assessmentQuizService;

	public JobService(JobsRepository jobRepository, EmployeeRepository employeeRepository,
			RequirementRepository requirementRepository, AssessmentQuizService assessmentQuizService) {
		this.jobRepository = jobRepository;
		this.employeeRepository = employeeRepository;
		this.requirementRepository = requirementRepository;
		this.assessmentQuizService = assessmentQuizService;
	}

	/**
	 * Insert a job
	 */
	public Job insertJob(JobDto jobDto) throws JobNameAlreadyExistsException {
		if (jobRepository.findByJobName(jobDto.getProjectName()) == null) {
			Job job = JobUtility.convertJobDtoToJob(jobDto); // Convert Dto into a Job Object
			Set<Requirement> requirements = new HashSet<>();
			for (String requirement : jobDto.getRequirements()) {//Save or update each requirement
				if (requirementRepository.findByName(requirement) != null) {
					requirements.add(requirementRepository.findByName(requirement));//Get Requirement if already exist
				} else {
					Requirement requirement2 = new Requirement();//Create a new Requirement record
					requirement2.setName(requirement);
					requirementRepository.save(requirement2);
					requirements.add(requirement2);
				}
			}
			job.setRequirements(requirements);
			job = jobRepository.save(job);
			for (String assignTo : jobDto.getAssignTos()) {//Assign job to each assignee
				if (employeeRepository.findByEmployeeName(assignTo) != null) {
					Employee employee = employeeRepository.findByEmployeeName(assignTo);
					employee.getJobs().add(job);
					employeeRepository.save(employee);
				}
			}
			return job;
		} else {
			throw new JobNameAlreadyExistsException("This job name already exists");
		}
	}

	/**
	 * 
	 * @param jobId
	 *            Find a job by id
	 */
	public Job findByJobId(Long jobId) {
		return jobRepository.findByJobId(jobId);
	}

	/**
	 * 
	 * @param jobName
	 *            Find a job by name
	 */
	public Job findByJobName(String jobName) {
		return jobRepository.findByJobName(jobName);
	}

	/**
	 * 
	 * Find all jobs
	 */
	public List<Job> findAllActiveJob() {
		return jobRepository.findByActiveJob(true);
	}

	/**
	 * 
	 * @param jobName
	 * @throws JobNotFoundException
	 *             Delete a job by its job name
	 */
	public void deleteJob(String jobName) throws JobNotFoundException {
		Job findJob = jobRepository.findByJobName(jobName);
		if (findJob != null) {
			jobRepository.delete(findJob);
			return;
		} else {
			throw new JobNotFoundException("Delete failed. No job found with the job Name:" + jobName);
		}
	}

	public Job updateJob(Long jobId, MultipartFile file)
			throws JobNotFoundException, IllegalStateException, IOException, InvalidFormatException {
		Job searchJob = jobRepository.findByJobId(jobId);
		if (searchJob != null) {
			File convFile = new File(file.getOriginalFilename());
			convFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(convFile);
			fos.write(file.getBytes());
			fos.close();
			List<QuestionDto> questionDtos = JobUtility.convertExcelToQuestionDto(file.getInputStream());
			QuizDto quizDto = new QuizDto();
			quizDto.setQuizName(file.getName());
			quizDto.setQuestions(questionDtos);
			AssessmentQuiz quiz = assessmentQuizService.insertAssessmentQuiz(quizDto);
			searchJob.setAssessmentQuiz(quiz);
			return jobRepository.save(searchJob);
		} else {
			throw new JobNotFoundException("Failed to update job");
		}
	}
	
	public Job updateJobWithQuiz(Long jobId, Long quizId) throws JobNotFoundException {
		Job searchJob = jobRepository.findByJobId(jobId);
		if (searchJob != null) {
			AssessmentQuiz quiz = assessmentQuizService.findById(quizId);
			searchJob.setAssessmentQuiz(quiz);
			return jobRepository.save(searchJob);
		}
		throw new JobNotFoundException("Failed to update job");
	}

	public QuizDto findQuiz(long jobId)
			throws JobNotFoundException, EncryptedDocumentException, InvalidFormatException, IOException {
		QuizDto quizDto = new QuizDto();
		List<QuestionDto> questionDtos = new ArrayList<>();
		Job searchJob = jobRepository.findByJobId(jobId);
		if (searchJob != null) {
			Set<QuizQuestion> quizQuestions = searchJob.getAssessmentQuiz().getQuizQuestions();
			for (QuizQuestion quizQuestion : quizQuestions) {
				QuestionDto questionDto = new QuestionDto();
				questionDto.setQuestion(quizQuestion.getQuestion());
				questionDto.setCorrectAnswer(quizQuestion.getCorrectAnswer());
				questionDto.setMark(quizQuestion.getMark());
				questionDto.setAnswers(quizQuestion.getPossibleAnswers());
				questionDtos.add(questionDto);
			}
			quizDto.setQuizName(searchJob.getAssessmentQuiz().getQuizName());
			quizDto.setQuestions(questionDtos);
			return quizDto;
		} else {
			throw new JobNotFoundException("Failed to update job");
		}
	}

	public List<Job> findActiveJobs(String eid) throws JobNotFoundException {
		List<BigInteger> jobIds = jobRepository.findActiveJob(eid);

		if (jobIds.isEmpty()) {
			throw new JobNotFoundException("Active Job Not found");
		}

		List<Job> allJobs = new ArrayList<>();
		for (BigInteger jobId : jobIds) {
			allJobs.add(jobRepository.findByJobId(jobId.longValue()));
		}
		return allJobs;
	}

}
