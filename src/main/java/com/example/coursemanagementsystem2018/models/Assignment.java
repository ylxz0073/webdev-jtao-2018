package com.example.coursemanagementsystem2018.models;

import javax.persistence.Entity;

@Entity
public class Assignment extends Widget {
	private String assignmentTitle;
	private String paragraph;
	private String points;
	
	public String getPoints() {
		return points;
	}
	public void setPoints(String points) {
		this.points = points;
	}
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
