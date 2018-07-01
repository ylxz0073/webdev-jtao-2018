package com.example.coursemanagementsystem2018.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
public class Assignment extends Widget {
	private String assignmentTitle;
	private String paragraph;
	
	public String getAssignmentTitle() {
		return assignmentTitle;
	}
	public void setAssignmentTitle(String assignmentTitle) {
		this.assignmentTitle = assignmentTitle;
	}
	public String getParagraph() {
		return paragraph;
	}
	public void setParagraph(String paragraph) {
		this.paragraph = paragraph;
	}
}
