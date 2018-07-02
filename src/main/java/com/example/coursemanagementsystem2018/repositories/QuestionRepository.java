package com.example.coursemanagementsystem2018.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.coursemanagementsystem2018.models.BaseExamQuestion;


public interface QuestionRepository extends CrudRepository<BaseExamQuestion, Integer>{
	
	
}

