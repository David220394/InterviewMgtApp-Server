package com.accenture.interviewproj.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // Indicate that its a Modal Class
@Table(name = "TABLE_NAME") // Set table name
public class Example {

	@Id // Indicate that this attribute is the primary key
	@GeneratedValue(strategy = GenerationType.AUTO) // Auto Generate its value
	private Long exampleId;

	@Column(name = "NAME", nullable = false) // Indicate the name of the field + Constraint
	private String exampleName;

	public Example() {

	}

	public Long getExampleId() {
		return exampleId;
	}

	public void setExampleId(Long exampleId) {
		this.exampleId = exampleId;
	}

	public String getExampleName() {
		return exampleName;
	}

	public void setExampleName(String exampleName) {
		this.exampleName = exampleName;
	}

}
