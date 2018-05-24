package com.example.coursemanagementsystem2018.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.coursemanagementsystem2018.models.User;
import com.example.coursemanagementsystem2018.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;



@RestController
public class UserService {
	@Autowired
	UserRepository repository;
	
	
	@GetMapping("/api/user")
	public List<User> findAllUsers(@RequestParam(name="username", required=false) String username, @RequestParam(name="password", required=false) String password) {
		if (username != null && password != null) {
			return (List<User>)repository.findUserByUsernameAndPassword(username, password);
		} else if (username != null) {
			return (List<User>)repository.findUserByUsername(username);
		}
		return (List<User>)repository.findAll();
	}
	
	@GetMapping("/api/user/{userId}")
	public User findUserById(@PathVariable("userId") int id) {
		Optional<User> data = repository.findById(id);
		
		if (data.isPresent()) {
			System.out.println(data.get().getUsername());
			return data.get();
		}
		return null;
	}
	
	@PutMapping("/api/user/{userId}")
	public User updateUser(@PathVariable("userId") int userId, @RequestBody User newUser ) {
		Optional<User> data = repository.findById(userId);
		if (data.isPresent()) {
			User user = data.get();
			user.setFirstName(newUser.getFirstName());
			user.setLastName(newUser.getLastName());
			user.setUsername(newUser.getUsername());
			user.setPassword(newUser.getPassword());
			user.setRole(newUser.getRole());
			repository.save(user);
			return user;
		}
		return null;
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
//	public Iterable<User> findUserByUsername(@PathVariable("username") String username) {
//		return repository.findUserByUsername(username);
//	}
	
	@PostMapping("/api/register")
	public User register(@RequestBody User user, HttpSession session) throws Exception{
//		System.out.println("###########" + ((List<User>)findUserByUsername(user.getUsername())).size() + "############");
		if (((List<User>)findAllUsers(user.getUsername(), null)).size() != 0) {
			throw new Exception("duplicate username");
		}
		repository.save(user);
		session.setAttribute("user", user);
		return user;
		
	}
	
	@PostMapping("/api/login")
	public User login(@RequestBody User user, HttpSession session) throws Exception{
		List<User> results = ((List<User>)repository.findUserByUsernameAndPassword(user.getUsername(), user.getPassword()));
//		System.out.println("##########" + results.size() + "#########");
		if (results.size() == 0) {
			throw new Exception("can't find matched credential");
			
		}
		session.setAttribute("user", user);
		return results.get(0);
	}
	
	
	
	@PutMapping("/api/profile")
	public User updateProfile(@RequestBody User user, HttpSession session) {
		User userFound = (User)session.getAttribute("user");
		if ( userFound!= null) {
			userFound.setDateOfBirth(user.getDateOfBirth());
			userFound.setEmail(user.getEmail());
			userFound.setPhone(user.getPhone());
			userFound.setRole(user.getRole());
		}
		return null;
	}
	
	@PostMapping("/api/logout")
	public User login(HttpSession session) {
		User cur = (User)session.getAttribute("user");
		session.setAttribute("user", null);
		return cur;
	}

}