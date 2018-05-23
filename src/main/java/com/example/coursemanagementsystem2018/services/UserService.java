package com.example.coursemanagementsystem2018.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.coursemanagementsystem2018.models.User;
import com.example.coursemanagementsystem2018.repositories.UserRepository;

import java.util.List;
import java.util.Optional;


@RestController
public class UserService {
	@Autowired
	UserRepository repository;
	
	
	@GetMapping("/api/user")
	public List<User> findAllUsers() {
		return (List)repository.findAll();
	}
	

}