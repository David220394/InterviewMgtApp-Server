package com.accenture.interviewproj.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name= "QUIZ_QUESTION_TABLE")
public class QuizQuestion implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="QUIZ_QUESTION_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long quizQuestionId;
	
	@Column(name="QUESTION")
	@Lob
	private String question;
	
	@Column(name="POSSIBLE_ANSWER")
	@ElementCollection
	private List<String> possibleAnswers;
	
	@Column(name="CORRECT_ANSWER")
	private String correctAnswer;
	
	@Column(name="MARK")
	private int mark;

	@ManyToOne
	@JoinColumn(name="ASSESSMENT_QUIZ")
	private AssessmentQuiz assessmentQuiz; 
	
	public Long getQuizQuestionId() {
		return quizQuestionId;
	}

	public void setQuizQuestionId(Long quizQuestionId) {
		this.quizQuestionId = quizQuestionId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public List<String> getPossibleAnswers() {
		return possibleAnswers;
	}

	public void setPossibleAnswers(List<String> possibleAnswers) {
		this.possibleAnswers = possibleAnswers;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	public AssessmentQuiz getAssessmentQuiz() {
		return assessmentQuiz;
	}

	public void setAssessmentQuiz(AssessmentQuiz assessmentQuiz) {
		this.assessmentQuiz = assessmentQuiz;
	}

	
	
}
