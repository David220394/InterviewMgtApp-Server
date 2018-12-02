package com.accenture.interviewproj.utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.web.multipart.MultipartFile;

import com.accenture.interviewproj.dtos.CandidateDto;
import com.accenture.interviewproj.dtos.DisplayCandidateDto;
import com.accenture.interviewproj.entities.Candidate;
import com.accenture.interviewproj.enums.Gender;

public class CandidateUtility {
	
	private static String uploadCVFolder = System.getProperty("user.dir")
			+ "/src/main/resources/CandidateCv";
	
	private CandidateUtility() {}
	
	public static DisplayCandidateDto convertCandidateToDto(Candidate candidate) {
		DisplayCandidateDto candidateDto = new DisplayCandidateDto();
		candidateDto.setCandidateId(candidate.getCandidateId());
		candidateDto.setCandidateAddress(candidate.getCandidateAddress());
		candidateDto.setCandidateExperience(candidate.getCandidateExperience());
		candidateDto.setCandidateName(candidate.getCandidateName());
		candidateDto.setCandidatePhone(candidate.getCandidatePhone());
		candidateDto.setAvailability(candidate.getAvailability());
		candidateDto.setEmail(candidate.getEmail());
		candidateDto.setCoverLetter(candidate.getCoverLetter());
		candidateDto.setSkills(candidate.getSkills());
		candidateDto.setApplicationDate(candidate.getApplicationDate());
		candidateDto.setCompleteApplication(candidate.getCompleteApplication());
		candidateDto.setDob(candidate.getDob());
		candidateDto.setEducation(candidate.getEducation());
		candidateDto.setGender(candidate.getGender());
		candidateDto.setRehire(candidate.getRehire());
		candidateDto.setInternalApplication(candidate.getInternalApplication());
		return candidateDto;
		
	}
	
	public static CandidateDto getCandidateFromExcel(Row row) {
		List<String> skills = new ArrayList<>();
		CandidateDto candidateDto = new CandidateDto();
		candidateDto.setJobName(row.getCell(0).getStringCellValue());
		candidateDto.setCandidateName(row.getCell(1).getStringCellValue());
		candidateDto.setCandidateAddress(row.getCell(2).getStringCellValue()+" "+row.getCell(3).getStringCellValue());
		candidateDto.setGender(Gender.valueOf(row.getCell(4).getStringCellValue().toUpperCase()));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		candidateDto.setDob(LocalDate.parse(row.getCell(5).getStringCellValue(),formatter));
		candidateDto.setEmail(row.getCell(6).getStringCellValue());
		candidateDto.setCandidatePhone((long) row.getCell(7).getNumericCellValue());
		candidateDto.setApplicationDate(LocalDate.parse(row.getCell(8).getStringCellValue(),formatter).atStartOfDay());
		candidateDto.setCompleteApplication(row.getCell(9).getBooleanCellValue());
		candidateDto.setInternalApplication(row.getCell(10).getBooleanCellValue());
		candidateDto.setRehire(row.getCell(11).getBooleanCellValue());
		candidateDto.setStatus(row.getCell(12).getStringCellValue());
		candidateDto.setSpecialty(row.getCell(14).getStringCellValue());
		candidateDto.setLastJobRole(row.getCell(15).getStringCellValue());
		candidateDto.setEmployer(row.getCell(16).getStringCellValue());
		candidateDto.setInstitution(row.getCell(17).getStringCellValue());
		candidateDto.setProgramStudy(row.getCell(18).getStringCellValue());
		candidateDto.setGrade(row.getCell(19).getNumericCellValue());
		skills.add(row.getCell(20).getStringCellValue());
		candidateDto.setSkills(skills);
		return candidateDto;
	}
	
	public static Candidate convertCandidateDtoToCandidate(CandidateDto candidateDto) {
		Candidate candidate = new Candidate();
		candidate.setCandidateName(candidateDto.getCandidateName());
		candidate.setCandidateAddress(candidateDto.getCandidateAddress());
		candidate.setAvailability(true);
		candidate.setEmail(candidateDto.getEmail());
		candidate.setCoverLetter("Dear .. . .");
		candidate.setCandidatePhone(candidateDto.getCandidatePhone());
		candidate.setDob(candidateDto.getDob());
		candidate.setCandidateCv(candidateDto.getCandidateCv());
		candidate.setGender(candidateDto.getGender());
		candidate.setInternalApplication(candidateDto.getInternalApplication());
		candidate.setCompleteApplication(candidateDto.getCompleteApplication());
		candidate.setRehire(candidateDto.getRehire());
		return candidate;
	}

	public static void uploadCandidateCv(byte[] bytesprerequisiteProofOfcompletion,String filename) throws IOException{
		Path path = Paths.get(uploadCVFolder, filename);
		Files.write(path, bytesprerequisiteProofOfcompletion);
	}
}
