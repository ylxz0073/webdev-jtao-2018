package com.example.coursemanagementsystem2018.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

@Entity
public class MultipleChoiceQuestion extends BaseExamQuestion {
	@Column
	@ElementCollection(targetClass=String.class)
	private List<String> choices;
	private String correctChoice;
	public List<String> getChoices() {
		return choices;
	}
	public void setChoices(List<String> choices) {
		this.choices = choices;
	}
	public String getCorrectChoice() {
		return correctChoice;
	}
	public void setCorrectChoice(String correctChoice) {
		this.correctChoice = correctChoice;
	}
	
}
