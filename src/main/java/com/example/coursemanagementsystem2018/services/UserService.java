package com.example.coursemanagementsystem2018.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.coursemanagementsystem2018.models.User;
import com.example.coursemanagementsystem2018.repositories.UserRepository;

import java.util.List;

import javax.servlet.http.HttpSession;



@RestController
public class UserService {
	@Autowired
	UserRepository repository;
	
	
	@GetMapping("/api/user")
	public List<User> findAllUsers() {
		return (List<User>)repository.findAll();
	}
	
	@PostMapping("/api/user")
	public User createUser(@RequestBody User user) {
		return repository.save(user);
	}
	
	@DeleteMapping("/api/user/{userId}")
	public void deleteUser(@PathVariable("userId") int id) {
		repository.deleteById(id);
	}
	
//	@GetMapping("/api/user/{username}")
	public User findUserByUsername(@PathVariable("username") String username) {
		return repository.findUserByUsername(username);
	}
	
	@PostMapping("/api/register")
	public User register(@RequestBody User user, HttpSession session) {
		if (repository.findUserByUsername(user.getUsername()) == null) {
			repository.save(user);
			session.setAttribute("user", user);
		}
		return null;
	}

}