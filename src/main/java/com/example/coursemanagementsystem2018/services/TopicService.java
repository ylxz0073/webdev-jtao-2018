package com.example.coursemanagementsystem2018.services;

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

import com.example.coursemanagementsystem2018.models.Lesson;
import com.example.coursemanagementsystem2018.models.Module;
import com.example.coursemanagementsystem2018.models.Topic;
import com.example.coursemanagementsystem2018.repositories.CourseRepository;
import com.example.coursemanagementsystem2018.repositories.LessonRepository;
import com.example.coursemanagementsystem2018.repositories.ModuleRepository;
import com.example.coursemanagementsystem2018.repositories.TopicRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class TopicService {
	@Autowired
	CourseRepository courseRepository;
	@Autowired
	ModuleRepository moduleRepository;
	@Autowired
	LessonRepository lessonRepository;
	@Autowired
	TopicRepository topicRepository;
	
	@PostMapping("/api/course/{courseId}/module/{moduleId}/lesson/{lessonId}/topic")
	public Topic createTopic(@PathVariable("lessonId") int lessonId,
			@RequestBody Topic newTopic) {
			
			Optional<Lesson> data = lessonRepository.findById(lessonId);
			if(data.isPresent()) {
				Lesson lesson = data.get();
				newTopic.setLesson(lesson);
				return topicRepository.save(newTopic);
			}
			System.out.println("##### " + lessonId + " data not present #####");
			return null;

		}
	
	@GetMapping("/api/course/{courseId}/module/{moduleId}/lesson/{lessonId}/topic")
	public List<Topic> findAllTopicsForLesson(
			@PathVariable("lessonId") int lessonId){
		Optional<Lesson> data = lessonRepository.findById(lessonId);
		if(data.isPresent()) {
			Lesson lesson = data.get();
//			List<Lesson> lessons = module.getLessons();
//			System.out.println(lessons.get(0));
			return lesson.getTopics();
		}
		return null;
		
	}
	
	@GetMapping("/api/topic")
	public Iterable<Topic> findAllLessons() {
		return topicRepository.findAll(); 
	}
	
	@DeleteMapping("/api/course/{courseId}/module/{moduleId}/lesson/{lessonId}/topic/{topicId}")
	public void deleteTopic(
	  @PathVariable("topicId")
	    int topicId) {
		topicRepository.deleteById(topicId);
	}
	

}
