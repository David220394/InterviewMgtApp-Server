package com.accenture.interviewproj.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.accenture.interviewproj.dtos.QuestionDto;
import com.accenture.interviewproj.dtos.QuizDto;
import com.accenture.interviewproj.entities.AssessmentQuiz;
import com.accenture.interviewproj.entities.Candidate;
import com.accenture.interviewproj.entities.CandidateExperience;
import com.accenture.interviewproj.entities.CandidateSkillScore;
import com.accenture.interviewproj.entities.Education;
import com.accenture.interviewproj.entities.Employee;
import com.accenture.interviewproj.entities.Job;
import com.accenture.interviewproj.entities.JobCandidate;
import com.accenture.interviewproj.entities.Requirement;
import com.accenture.interviewproj.entities.Skill;
import com.accenture.interviewproj.entities.Status;
import com.accenture.interviewproj.enums.Gender;
import com.accenture.interviewproj.enums.Role;
import com.accenture.interviewproj.repositories.CandidateExperienceRepository;
import com.accenture.interviewproj.repositories.CandidateRepository;
import com.accenture.interviewproj.repositories.CandidateSkillScoreRepository;
import com.accenture.interviewproj.repositories.EducationRepository;
import com.accenture.interviewproj.repositories.EmployeeRepository;
import com.accenture.interviewproj.repositories.JobCandidateRepository;
import com.accenture.interviewproj.repositories.JobsRepository;
import com.accenture.interviewproj.repositories.RequirementRepository;
import com.accenture.interviewproj.repositories.SkillRepository;
import com.accenture.interviewproj.repositories.StatusRepository;
import com.accenture.interviewproj.utilities.JobUtility;

@Service
public class InitService {
	
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
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;
	
	
	/**
	 * Details for job and candidate
	 */
	@PostConstruct
	public void init() {
		if(candidateRepository.findAll().isEmpty()) {
			
		Set<Requirement> requirements = new HashSet<>();	
		Set<Requirement> requirements1 = new HashSet<>();
		Requirement requirement = new Requirement();
		requirement.setName("java");
		requirementRepository.save(requirement);
		requirements.add(requirement);
		requirements1.add(requirement);
		
		Requirement requirement1 = new Requirement();
		requirement1.setName(".net");
		requirementRepository.save(requirement1);
		requirements.add(requirement1);
		
		Requirement requirement2 = new Requirement();
		requirement2.setName("Shell Scripting");
		requirementRepository.save(requirement2);
		requirements1.add(requirement2);
		AssessmentQuiz assessmentQuiz = null;
		try {
			FileInputStream file = new FileInputStream("Test1.xlsx");
			List<QuestionDto> dtos = JobUtility.convertExcelToQuestionDto(file);
			QuizDto quizDto = new QuizDto();
			quizDto.setQuizName("Test1");
			quizDto.setQuestions(dtos);
			assessmentQuiz = assessmentQuizService.insertAssessmentQuiz(quizDto);
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
		}
		
		Job job = new Job();
		job.setActiveJob(true);
		job.setJobName("Accenture Academy");
		job.setLocation("NexTracom");
		job.setAssessmentQuiz(assessmentQuiz);
		job.setPosition("Java Developer");
		job.setRequirements(requirements);
		jobRepository.save(job);

		Job job1 = new Job();
		job1.setActiveJob(true);
		job1.setJobName("SAP Development");
		job1.setLocation("NexTracom");
		job1.setAssessmentQuiz(assessmentQuiz);
		job1.setPosition("SAP Developer");
		job1.setRequirements(requirements1);
		jobRepository.save(job1);
		
		List<Job> jobs1 = new ArrayList<>();
		jobs1.add(job);
		jobs1.add(job1);
		List<Job> jobs2 = new ArrayList<>();
		jobs2.add(job);
		
		Employee employee = new Employee();
		employee.setEmployeeId("sylvio.brandon.david");
		employee.setEmployeeName("Brandon David");
		employee.setEmployeePassword(bcryptEncoder.encode("12345"));
		employee.setRole(Role.ADMIN);
		employee.setJobs(jobs1);
		employeeRepository.save(employee);
		
		Employee employee1 = new Employee();
		employee1.setEmployeeId("hemanta.huril");
		employee1.setEmployeeName("Hemanta Huril");
		employee1.setEmployeePassword(bcryptEncoder.encode("12345"));
		employee1.setRole(Role.HR);
		employee1.setJobs(jobs2);
		employeeRepository.save(employee1);
		
		Set<Skill> skills = new HashSet<>();
		Set<Skill> skills1 = new HashSet<>();
		Skill skill = new Skill();
		skill.setDescription("java");
		skillRepository.save(skill);
		skills.add(skill);
		skills1.add(skill);
		
		Skill skill1 = new Skill();
		skill1.setDescription(".net");
		skillRepository.save(skill1);
		skills.add(skill1);
		
		Candidate candidate = new Candidate();
		candidate.setCandidateName("Brandon");
		candidate.setCandidateAddress("Beau Bassin");
		candidate.setAvailability(true);
		candidate.setEmail("brandondavid220394@gmail.com");
		candidate.setCoverLetter("Dear .. . .");
		candidate.setCandidatePhone((long) 57565279);
		candidate.setSkills(skills);
		candidate.setDob(LocalDate.of(1994, 3, 22));
		candidate.setGender(Gender.MALE);
		candidate.setCompleteApplication(false);
		candidate.setInternalApplication(false);
		candidate.setRehire(false);
		candidate.setCandidateCv("david.pdf");
		candidateRepository.save(candidate);
		
		Candidate candidate1 = new Candidate();
		candidate1.setCandidateName("Hemanta");
		candidate1.setCandidateAddress("Triolet");
		candidate1.setAvailability(true);
		candidate1.setEmail("hemantahuril220394@gmail.com");
		candidate1.setCoverLetter("Hi .. . .");
		candidate1.setSkills(skills);
		candidate1.setCandidatePhone((long) 5754363);
		candidate1.setDob(LocalDate.of(1994, 3, 22));
		candidate1.setGender(Gender.FEMALE);
		candidate1.setCompleteApplication(true);
		candidate1.setInternalApplication(false);
		candidate1.setRehire(true);
		candidate.setCandidateCv("huril.pdf");
		candidateRepository.save(candidate1);
		
		Candidate candidate2 = new Candidate();
		candidate2.setCandidateName("Khaushik");
		candidate2.setCandidateAddress("Pheonix");
		candidate2.setAvailability(true);
		candidate2.setEmail("l.jankee@gmail.com");
		candidate2.setCoverLetter("Dear .. . .");
		candidate2.setSkills(skills1);
		candidate2.setCandidatePhone((long) 5723452);
		candidate2.setDob(LocalDate.of(1994, 3, 22));
		candidate2.setGender(Gender.MALE);
		candidate2.setCompleteApplication(true);
		candidate2.setInternalApplication(true);
		candidate2.setRehire(false);
		candidate.setCandidateCv("jankee.pdf");
		candidateRepository.save(candidate2);
		
		List <Job> jobs = jobRepository.findAll();
		
		JobCandidate jobCandidate = new JobCandidate();
		jobCandidate.setCandidate(candidate);
		jobCandidate.setJob(job);
		jobCandidate.setScore(0);
		jobCandidateRepository.save(jobCandidate);
		
		for (Job job2 : jobs) {
			CandidateSkillScore candidateSkillScore = new CandidateSkillScore();
			candidateSkillScore.setCandidate(candidate);
			candidateSkillScore.setJob(job2);
			candidateSkillScoreRepository.save(candidateSkillScore);
		}
		
		JobCandidate jobCandidate1 = new JobCandidate();
		jobCandidate1.setCandidate(candidate1);
		jobCandidate1.setJob(job);
		jobCandidate1.setScore(0);
		jobCandidateRepository.save(jobCandidate1);
		
		for (Job job2 : jobs) {
			CandidateSkillScore candidateSkillScore = new CandidateSkillScore();
			candidateSkillScore.setCandidate(candidate1);
			candidateSkillScore.setJob(job2);
			candidateSkillScoreRepository.save(candidateSkillScore);
		}
		
		JobCandidate jobCandidate2 = new JobCandidate();
		jobCandidate2.setCandidate(candidate2);
		jobCandidate2.setJob(job1);
		jobCandidate2.setScore(0);
		jobCandidateRepository.save(jobCandidate2);

		for (Job job2 : jobs) {
			CandidateSkillScore candidateSkillScore = new CandidateSkillScore();
			candidateSkillScore.setCandidate(candidate2);
			candidateSkillScore.setJob(job2);
			candidateSkillScoreRepository.save(candidateSkillScore);
		}
		
		Status status = new Status();
		status.setStatusName("Applied");
		status.setCandidate(jobCandidate);
		statusRepository.save(status);
		
		Status status1 = new Status();
		status1.setStatusName("Applied");
		status1.setCandidate(jobCandidate1);
		statusRepository.save(status1);
		
		Status status2 = new Status();
		status2.setStatusName("Applied");
		status2.setCandidate(jobCandidate2);
		statusRepository.save(status2);
		
		Education education = new Education();
		education.setInstitutionName("UOM");
		education.setProgramStudy("Mtx");
		education.setGrade(69.7);
		education.setCandidate(candidate);
		educationRepository.save(education);
		
		Education education1 = new Education();
		education1.setInstitutionName("Pune");
		education1.setProgramStudy("BSc Electronics");
		education1.setGrade(69.7);
		education1.setCandidate(candidate1);
		educationRepository.save(education1);
		
		Education education2 = new Education();
		education2.setInstitutionName("China Unicersity");
		education2.setProgramStudy("MBa");
		education2.setGrade(69.7);
		education2.setCandidate(candidate2);
		educationRepository.save(education2);
		
		CandidateExperience candidateExperience = new CandidateExperience();
		candidateExperience.setExperienceName("Trainee Engineer");
		candidateExperience.setSpecialty("Mechanical And Electronics");
		candidateExperience.setLocation("CMT");
		candidateExperience.setCandidate(candidate);
		candidateExperienceRepository.save(candidateExperience);
		
		CandidateExperience candidateExperience1 = new CandidateExperience();
		candidateExperience1.setExperienceName("Trainee");
		candidateExperience1.setSpecialty("Electronics");
		candidateExperience1.setLocation("Linkopia");
		candidateExperience1.setCandidate(candidate1);
		candidateExperienceRepository.save(candidateExperience1);
		
		CandidateExperience candidateExperience2 = new CandidateExperience();
		candidateExperience2.setExperienceName("Trainee");
		candidateExperience2.setSpecialty("Aviation");
		candidateExperience2.setLocation("SSR Airport");
		candidateExperience2.setCandidate(candidate2);
		candidateExperienceRepository.save(candidateExperience2);

		
		}
		
	}

}
