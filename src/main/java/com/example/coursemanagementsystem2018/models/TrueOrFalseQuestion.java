package com.example.coursemanagementsystem2018.models;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class TrueOrFalseQuestion extends BaseExamQuestion {
	
	private Boolean isTrue;

	public Boolean getIsTrue() {
		return isTrue;
	}

	public void setIsTrue(Boolean isTrue) {
		this.isTrue = isTrue;
	}

	

	
}
