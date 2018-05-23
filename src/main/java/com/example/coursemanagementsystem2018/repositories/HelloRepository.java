package com.example.coursemanagementsystem2018.repositories;

import org.springframework.data.repository.CrudRepository;
import com.example.coursemanagementsystem2018.models.Hello;

public interface HelloRepository 
	extends CrudRepository<Hello, Integer>{

}
