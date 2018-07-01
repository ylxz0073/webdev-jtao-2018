package com.example.coursemanagementsystem2018.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.coursemanagementsystem2018.models.Assignment;
import com.example.coursemanagementsystem2018.models.Widget;

public interface AssignmentRepository extends CrudRepository<Assignment, Integer>{
	
	
}
