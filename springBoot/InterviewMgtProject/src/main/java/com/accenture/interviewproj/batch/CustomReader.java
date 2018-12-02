package com.accenture.interviewproj.batch;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.IteratorItemReader;
/**
 *Custom Reader class 
 * To read input from batch process
 */
public class CustomReader implements ItemReader<Row> {
	
	private ItemReader<Row> delegate;
	
	String path;
	
	public CustomReader(String path){
		this.path = path;
	}

	@Override
	public Row read()
			throws Exception {
		if(delegate == null) {
			delegate = new IteratorItemReader<>(candidates());
		}
		return delegate.read();
	}
	
	/**
	 * Method to obtain every row from every excel files
	 */
	private List<Row> candidates() throws EncryptedDocumentException, 
	InvalidFormatException, IOException{
		List<Row> rows = new ArrayList<>();
		File folder = new File(this.path);
		if(folder.isDirectory()) {
			File[] files = folder.listFiles();
			for (File file : files) {
				if(!(file.isDirectory()) && 
						FilenameUtils.
						getExtension(file.getName()).equals("xlsx")) {
					rows.addAll(candidateInfo(file));
				}
			}
		}
		return rows;
	}
	/**
	 * Method to extract each row from each excel files
	 * 
	 */
	private List<Row> candidateInfo(File file) throws EncryptedDocumentException, InvalidFormatException, IOException{
		List<Row> rows = new ArrayList<>();
		Workbook workbook =  WorkbookFactory.create(file);
		Sheet sheet = workbook.getSheetAt(0);
		DataFormatter dataFormatter = new DataFormatter();
		Iterator<Row> rowIterator = sheet.rowIterator();
		if(!rowIterator.hasNext()) {
			return rows;
		}
		rowIterator.next();
		while(rowIterator.hasNext()) {
			rows.add(rowIterator.next());
		}
		return rows;
	}
		
	

}
