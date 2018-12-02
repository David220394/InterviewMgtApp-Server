package com.accenture.interviewproj.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.interviewproj.dtos.AfterInterviewDto;
import com.accenture.interviewproj.dtos.InterviewDto;
import com.accenture.interviewproj.dtos.InterviewQuestionDto;
import com.accenture.interviewproj.dtos.ScoreDto;
import com.accenture.interviewproj.entities.AssessmentInterview;
import com.accenture.interviewproj.entities.Candidate;
import com.accenture.interviewproj.entities.HRInterview;
import com.accenture.interviewproj.entities.Interview;
import com.accenture.interviewproj.entities.InterviewQuestion;
import com.accenture.interviewproj.entities.Job;
import com.accenture.interviewproj.entities.Question;
import com.accenture.interviewproj.entities.TechnicalInterview;
import com.accenture.interviewproj.enums.IntType;
import com.accenture.interviewproj.exceptions.ExpiredLinkException;
import com.accenture.interviewproj.exceptions.IdNotFoundException;
import com.accenture.interviewproj.exceptions.TypeNotFoundException;
import com.accenture.interviewproj.repositories.AssessmentInterviewRepository;
import com.accenture.interviewproj.repositories.CandidateRepository;
import com.accenture.interviewproj.repositories.HRInterviewRepository;
import com.accenture.interviewproj.repositories.InterviewQuestionRepository;
import com.accenture.interviewproj.repositories.InterviewRepository;
import com.accenture.interviewproj.repositories.JobsRepository;
import com.accenture.interviewproj.repositories.QuestionRepository;
import com.accenture.interviewproj.repositories.TechnicalInterviewRepository;

@Service
public class InterviewService {

	@Autowired
	private HRInterviewRepository hrInterviewRepository;

	@Autowired
	private TechnicalInterviewRepository technicalInterviewRepository;

	@Autowired
	private AssessmentInterviewRepository assessmentInterviewRepository;

	@Autowired
	private InterviewRepository interviewRepository;

	@Autowired
	private CandidateRepository candidateRepository;

	@Autowired
	private JobsRepository jobsRepository;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private InterviewQuestionRepository interviewQuestionRepository;

	public Interview createInterview(InterviewDto interviewDto) throws TypeNotFoundException {
		if (interviewDto.getType() != null) {
			Candidate candidate = candidateRepository.findById(interviewDto.getCandidateId()).get();
			Job job = jobsRepository.findByJobId(interviewDto.getJobId());
			if ("hr".equals(interviewDto.getType())) {
				return createHrInterview(interviewDto, candidate, job);
			} else if ("tech".equals(interviewDto.getType())) {
				return createTechnicalInterview(interviewDto, candidate, job);
			} else if ("iq".equals(interviewDto.getType())) {
				return createAssessmentInterview(interviewDto, candidate, job);
			}
		}

		throw new TypeNotFoundException("Interview Type not set");
	}

	private Interview createHrInterview(InterviewDto interviewDto, Candidate candidate, Job job) {
		HRInterview interview = new HRInterview();
		interview.setInterviewer(interviewDto.getInterviewer());
		interview.setCompleted(false);
		interview.setMaxScore(0.0);
		interview.setCandidate(candidate);
		interview.setJob(job);
		interview.setLink(interviewDto.getLink());
		return hrInterviewRepository.save(interview);
	}

	private Interview createTechnicalInterview(InterviewDto interviewDto, Candidate candidate, Job job) {
		TechnicalInterview interview = new TechnicalInterview();
		interview.setInterviewer(interviewDto.getInterviewer());
		interview.setCompleted(false);
		interview.setMaxScore(0.0);
		interview.setCandidate(candidate);
		interview.setJob(job);
		interview.setLink(interviewDto.getLink());
		return technicalInterviewRepository.save(interview);
	}

	private Interview createAssessmentInterview(InterviewDto interviewDto, Candidate candidate, Job job) {
		AssessmentInterview interview = new AssessmentInterview();
		interview.setCandidate(candidate);
		interview.setCompleted(false);
		interview.setMaxScore(0.0);
		interview.setJob(job);
		interview.setLink(interviewDto.getLink());
		return assessmentInterviewRepository.save(interview);
	}

	public Interview updateInterview(AfterInterviewDto afterInterviewDto) throws TypeNotFoundException {
		if (afterInterviewDto.getType() != null) {
			if ("HR".equals(afterInterviewDto.getType())) {
				HRInterview interview = hrInterviewRepository.findByLink(afterInterviewDto.getLink());
				interview.setScore(afterInterviewDto.getScore());
				interview.setFeedback(afterInterviewDto.getFeedback());
				interview.setMaxScore(afterInterviewDto.getMaxScore());
				interview.setCompleted(true);
				hrInterviewRepository.save(interview);
				updateInterviewQuestion(afterInterviewDto, interview);
				return interview;
			} else if ("TECH".equals(afterInterviewDto.getType())) {
				TechnicalInterview interview = technicalInterviewRepository.findByLink(afterInterviewDto.getLink());
				interview.setScore(afterInterviewDto.getScore());
				interview.setMaxScore(afterInterviewDto.getMaxScore());
				interview.setFeedback(afterInterviewDto.getFeedback());
				interview.setCompleted(true);
				technicalInterviewRepository.save(interview);
				updateInterviewQuestion(afterInterviewDto, interview);
				return interview;
			} else if ("iq".equals(afterInterviewDto.getType())) {
				AssessmentInterview interview = assessmentInterviewRepository.findByLink(afterInterviewDto.getLink());
				interview.setScore(afterInterviewDto.getScore());
				interview.setMaxScore(afterInterviewDto.getMaxScore());
				interview.setCompleted(true);
				return assessmentInterviewRepository.save(interview);
			}
		}
		throw new TypeNotFoundException("Interview Type not set");
	}
	
	
	private void updateInterviewQuestion(AfterInterviewDto afterInterviewDto, Interview interview) {
		List<InterviewQuestionDto> interviewQuestionDtos = afterInterviewDto.getQuestions();
		for (InterviewQuestionDto interviewQuestionDto : interviewQuestionDtos) {
			InterviewQuestion interviewQuestion = new InterviewQuestion();
			interviewQuestion.setInterview(interview);
			interviewQuestion.setComment(interviewQuestionDto.getComment());
			interviewQuestion.setMark(interviewQuestionDto.getMark());
			Question question = questionRepository.findByQuestion(interviewQuestionDto.getQuestion());
			if(question == null) {
				question = new Question();
				question.setQuestion(interviewQuestionDto.getQuestion());
				questionRepository.save(question);
			}
			interviewQuestion.setQuestion(question);
			interviewQuestionRepository.save(interviewQuestion);
		}
	}
	/**
	 * Find Interview by link
	 *if link valid, return Interview Object
	 * else throws ExpiredLinkException
	 */
	public Interview findByLink(String link) throws ExpiredLinkException {
		Interview interview = interviewRepository.findByLink(link);
		if (interview != null) {
			if(!(interview.getCompleted())) {
			return interview;
			}
		}
		throw new ExpiredLinkException("The Link have already expired");
	}
	/**
	 * Update Creation and Completed Interview date
	 * Depending on the type of interview, Return interview Object
	 */
	public Interview updateTime(Interview interview) {
		interview.setCreationDateTime(LocalDateTime.now());
		if(interview.getType().equals(IntType.ASSESSMENT)) {
			interview.setEndDateTime(LocalDateTime.now().plusHours(1));
		}else {
		interview.setEndDateTime(LocalDateTime.now().plusDays(1));
		}
		return interviewRepository.save(interview);
	}
	
	public List<Interview> findByCompletedAndCandidateIdAndJobId(long candidateId, long jobId) throws IdNotFoundException{
		if(candidateRepository.findByJobIdAndCandidateId(jobId, candidateId) != null) {
		List<Interview> interviews = interviewRepository.findInterviewByCompletedAndCandidateIdAndJobId(candidateId, jobId);
		return interviews;
		}else {
			throw new IdNotFoundException("Invalid jodId or candidate Id");
		}
	}
	
	public ScoreDto findScore(long candidateId, long jobId) throws IdNotFoundException{
		double hrScore = 0.0;
		double techScore = 0.0;
		double iqScore = 0.0;
		if(candidateRepository.findByJobIdAndCandidateId(jobId, candidateId) != null) {
			if(!interviewRepository.findByTypeOrderByCreationDate("HR", candidateId, jobId).isEmpty()) {
				Interview hrInterview = interviewRepository.findByTypeOrderByCreationDate("HR", candidateId, jobId).get(0);
				if(hrInterview.getScore() != null) {
				hrScore = (hrInterview.getScore() / hrInterview.getMaxScore()) * 100;
				}
			}
			if(!interviewRepository.findByTypeOrderByCreationDate("TECH", candidateId, jobId).isEmpty()) {
				Interview techInterview = interviewRepository.findByTypeOrderByCreationDate("TECH", candidateId, jobId).get(0);
				if(techInterview.getScore() != null) {
				techScore = (techInterview.getScore() / techInterview.getMaxScore()) * 100;
				}
			}
			if(!interviewRepository.findByTypeOrderByCreationDate("ASSESSMENT", candidateId, jobId).isEmpty()) {
				Interview iqInterview = interviewRepository.findByTypeOrderByCreationDate("ASSESSMENT", candidateId, jobId).get(0);
				if(iqInterview.getScore() != null) {
				iqScore = (iqInterview.getScore() / iqInterview.getMaxScore()) * 100;
				}
			}
			double aveScore = (0.5 * iqScore) + (0.25 * hrScore) + (0.25 * techScore);
			ScoreDto scoreDto = new ScoreDto();
			scoreDto.setHrScore(hrScore);
			scoreDto.setTechScore(techScore);
			scoreDto.setIqScore(iqScore);
			scoreDto.setAveScore(aveScore);
			
			return scoreDto;
			
			}else {
				throw new IdNotFoundException("Invalid jodId or candidate Id");
			}
	}

}
