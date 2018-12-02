package com.accenture.interviewproj.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.accenture.interviewproj.dtos.JobDto;
import com.accenture.interviewproj.dtos.JobWithIdDto;
import com.accenture.interviewproj.dtos.QuestionDto;
import com.accenture.interviewproj.entities.Job;

public class JobUtility {
	
	/**
	 * 
	 * @param jobDto
	 * Convert jobDto to job
	 */
	public static Job convertJobDtoToJob(JobDto jobDto) {
		Job job = new Job();
		job.setJobName(jobDto.getProjectName());
		job.setPosition(jobDto.getPosition());
		job.setLocation(jobDto.getLocation());
		job.setClosingDate(jobDto.getClosingDate());
		job.setCreationDate(jobDto.getCreationDate());
		job.setActiveJob(true);
		job.setAssignTo(jobDto.getAssignTos());
		return job;
	}
	
	public static JobWithIdDto convertJobWithIdDtoToJob(Job job) {
		JobWithIdDto jobWithIdDto = new JobWithIdDto();
		jobWithIdDto.setJobId(job.getJobId());
		jobWithIdDto.setJobName(job.getJobName());
		jobWithIdDto.setPosition(job.getPosition());
		jobWithIdDto.setLocation(job.getLocation());
		jobWithIdDto.setClosingDate(job.getClosingDate());
		jobWithIdDto.setCreationDate(job.getCreationDate());
		jobWithIdDto.setActiveJob(true);
		jobWithIdDto.setAssignTo(job.getAssignTo());
		return  jobWithIdDto;
	}
	
	/*public static Requirement convertRequirementDtoToDto(RequirementDto requirementDto) {
		Requirement requirement = new Requirement();
		requirement.setName(requirementDto.getName());
		requirement.setDuration(requirementDto.getDuration());
		requirement.setMinRequirement(requirementDto.getMinRequirement());
		return requirement;
	}*/
	
	public static List<QuestionDto> convertExcelToQuestionDto(InputStream file) throws EncryptedDocumentException, InvalidFormatException, IOException {
		List<QuestionDto> questionDtos = new ArrayList<>();
		Workbook workbook = WorkbookFactory.create(file);
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.rowIterator();
		
		if(!rowIterator.hasNext()) {
			return null;
		}
		rowIterator.next();
		DataFormatter formatter = new DataFormatter();
		while(rowIterator.hasNext()) {
			Row row = rowIterator.next();
			QuestionDto questionDto = new QuestionDto();
			questionDto.setQuestion(row.getCell(0).getStringCellValue());
			questionDto.setCorrectAnswer(formatter.formatCellValue(row.getCell(1)));
			questionDto.setMark((int)row.getCell(2).getNumericCellValue());
			List<String> possibleAns = new ArrayList<>();
			for(int i=3;i<row.getLastCellNum();i++) {	
				possibleAns.add(formatter.formatCellValue(row.getCell(i)));
			}
			questionDto.setAnswers(possibleAns);
			questionDtos.add(questionDto);
		}
		return questionDtos;
	}

}
