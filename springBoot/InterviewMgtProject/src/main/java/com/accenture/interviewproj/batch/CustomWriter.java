package com.accenture.interviewproj.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.accenture.interviewproj.dtos.CandidateDto;
import com.accenture.interviewproj.services.CandidateService;

/**
 * Custom Writer class To read the output(CandidateDto) from processor Stored
 * the candidate in the database.
 */
@Component
public class CustomWriter implements ItemWriter<CandidateDto> {

	@Autowired
	private CandidateService candidateService;

	@Override
	public void write(List<? extends CandidateDto> items) throws Exception {
		for (CandidateDto candidateDto : items) {
			candidateService.createCandidateFromDto(candidateDto);
		}
	}

}
