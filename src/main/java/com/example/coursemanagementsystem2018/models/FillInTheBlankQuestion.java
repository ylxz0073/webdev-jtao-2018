package com.example.coursemanagementsystem2018.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

@Entity
public class FillInTheBlankQuestion extends BaseExamQuestion {
	@Column
	@ElementCollection(targetClass=String.class)
	private List<String> variables;

	public List<String> getVariables() {
		return variables;
	}

	public void setVariables(List<String> variables) {
		this.variables = variables;
	}
	
}
