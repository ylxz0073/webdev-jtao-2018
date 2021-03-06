package com.example.coursemanagementsystem2018.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.coursemanagementsystem2018.models.Course;
import com.example.coursemanagementsystem2018.models.Module;
import com.example.coursemanagementsystem2018.repositories.CourseRepository;
import com.example.coursemanagementsystem2018.repositories.ModuleRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ModuleService {
	@Autowired
	CourseRepository courseRepository;
	@Autowired
	ModuleRepository moduleRepository;
	
	@PostMapping("/api/course/{courseId}/module")
	public Module createModule(@PathVariable("courseId") int courseId,
			@RequestBody Module newModule) {
			Optional<Course> data = courseRepository.findById(courseId);
			if(data.isPresent()) {
				Course course = data.get();
				newModule.setCourse(course);
				newModule.getCourse().setModified(new Date());
				return moduleRepository.save(newModule);
			}
			return null;

		}
	
	@GetMapping("/api/course/{courseId}/module")
	public List<Module> findAllModulesForCourse(@PathVariable("courseId") int courseId){
		Optional<Course> data = courseRepository.findById(courseId);
		if(data.isPresent()) {
			Course course = data.get();
			return course.getModules();
		}
		return null;
	}
	
	@GetMapping("/api/module")
	public List<Module> findAllModules(){
		return (List<Module>)moduleRepository.findAll(); 
	}
	
	@DeleteMapping("/api/course/{courseId}/module/{mId}")
	public void deleteModule(
	  @PathVariable("mId")
	    int moduleId) {
		Optional<Module> module = moduleRepository.findById(moduleId);
		if(module.isPresent()) {
			Module data = module.get();
			data.getCourse().setModified(new Date());
		}
		moduleRepository.deleteById(moduleId);
	}
 
}

