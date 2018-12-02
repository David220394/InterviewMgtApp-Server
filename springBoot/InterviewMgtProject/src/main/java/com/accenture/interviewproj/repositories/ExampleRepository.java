package com.accenture.interviewproj.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.accenture.interviewproj.entities.Example;

public interface ExampleRepository extends JpaRepository<Example, Long>{
	
	//Simple find without query
	List<Example> findByExampleName(String exampleName);
	
	//find using SQL Query 
	@Query(value="SELECT * FROM TABLE_NAME WHERE NAME=?",nativeQuery=true)
	List<Example> findByExampleNameUsingQuery(String exampleName);

}
