package com.accenture.interviewproj.batch;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.accenture.interviewproj.dtos.CandidateDto;
import com.accenture.interviewproj.utilities.CandidateUtility;

/**
 *Custom Processor class 
 *To read output(Row) from the reader() process
 *To write (CandidateDto) to the writer() process
 */
@Component
public class CustomProcessor implements ItemProcessor<Row, CandidateDto>{
	
	private String path;
	
	public CustomProcessor() {	}
	
	//Overload constructor to take a path as input
	public CustomProcessor(String path) {
		this.path = path;
	}

	@Override
	public CandidateDto process(Row row) throws Exception {
		CandidateDto candidateDto = CandidateUtility.getCandidateFromExcel(row);
		Path path= Paths.get(this.path+"/cv/", row.getCell(1).getStringCellValue()+".pdf");
		Resource resource = null;
		try {
			resource = new UrlResource(path.toUri());
		} catch (MalformedURLException e) {	} 
		byte[] arry = Files.readAllBytes(path);
		CandidateUtility.uploadCandidateCv(arry,row.getCell(1).getStringCellValue()+".pdf");
		candidateDto.setCandidateCv(row.getCell(1).getStringCellValue()+".pdf");
		return candidateDto;
	}

}
