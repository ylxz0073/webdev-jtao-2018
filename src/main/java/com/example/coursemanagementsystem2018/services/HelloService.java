package com.example.coursemanagementsystem2018.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.coursemanagementsystem2018.models.Hello;
import com.example.coursemanagementsystem2018.repositories.HelloRepository;

@RestController
public class HelloService {
	@Autowired
	HelloRepository repository;
	@GetMapping("/api/hello")
	public Iterable<Hello> findAllHellos() {
		return repository.findAll();
	}


}
