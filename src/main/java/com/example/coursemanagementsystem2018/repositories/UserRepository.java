package com.example.coursemanagementsystem2018.repositories;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.coursemanagementsystem2018.models.User;


public interface UserRepository extends CrudRepository<User, Integer>{

	@Query("SELECT u FROM User u WHERE u.username=:username")
	User findUserByUsername(@Param("username") String username);
	
	@Query("SELECT u FROM User u WHERE u.username=:username AND u.password=:password")
	User findUserByUsernameAndPassword(
		@Param("username") String username, 
		@Param("password") String password);
}



