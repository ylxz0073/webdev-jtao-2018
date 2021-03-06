package com.example.coursemanagementsystem2018.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.coursemanagementsystem2018.models.Course;
import com.example.coursemanagementsystem2018.models.Lesson;
import com.example.coursemanagementsystem2018.models.Module;
import com.example.coursemanagementsystem2018.repositories.CourseRepository;
import com.example.coursemanagementsystem2018.repositories.LessonRepository;
import com.example.coursemanagementsystem2018.repositories.ModuleRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class LessonService {
	@Autowired
	CourseRepository courseRepository;
	@Autowired
	ModuleRepository moduleRepository;
	@Autowired
	LessonRepository lessonRepository;
	
	@PostMapping("/api/course/{courseId}/module/{moduleId}/lesson")
	public Lesson createLesson(@PathVariable("moduleId") int moduleId,
			@RequestBody Lesson newLesson) {
			
			Optional<Module> data = moduleRepository.findById(moduleId);
			if(data.isPresent()) {
				Module module = data.get();
				newLesson.setModule(module);
				newLesson.getModule().getCourse().setModified(new Date());
				return lessonRepository.save(newLesson);
			}
			System.out.println("##### " + moduleId + " data not present #####");
			return null;

		}
	
	@GetMapping("/api/course/{courseId}/module/{moduleId}/lesson")
	public List<Lesson> findAllLessonsForModule(
			@PathVariable("moduleId") int moduleId){
		Optional<Module> data = moduleRepository.findById(moduleId);
		if(data.isPresent()) {
			Module module = data.get();
//			List<Lesson> lessons = module.getLessons();
//			System.out.println(lessons.get(0));
			return module.getLessons();
		}
		return null;
		
	}
	
	@GetMapping("/api/lesson")
	public Iterable<Lesson> findAllLessons() {
		return lessonRepository.findAll(); 
	}
	
	@DeleteMapping("/api/course/{courseId}/module/{moduleId}/lesson/{lessonId}")
	public void deleteLesson(
	  @PathVariable("lessonId")
	    int lessonId) {
		Optional<Lesson> data = lessonRepository.findById(lessonId);
		if (data.isPresent()) {
			data.get().getModule().getCourse().setModified(new Date());
		}
		lessonRepository.deleteById(lessonId);
	}
	

}
