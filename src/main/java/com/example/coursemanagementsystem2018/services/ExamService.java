package com.example.coursemanagementsystem2018.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.coursemanagementsystem2018.models.Exam;
import com.example.coursemanagementsystem2018.models.Topic;
import com.example.coursemanagementsystem2018.models.Widget;

import com.example.coursemanagementsystem2018.repositories.ExamRepository;
import com.example.coursemanagementsystem2018.repositories.TopicRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ExamService {

	@Autowired
	ExamRepository repository;
	@Autowired
	TopicRepository topicRepository;
	
	@GetMapping("/api/exam")
	public List<Exam> findAllAssignments() {
		List<Exam> results = (List<Exam>)repository.findAll();
		
		return results;
	}
	
	@GetMapping("/api/topic/{topicId}/exam")
	public List<Exam> findAllAssignmentsForTopic(
			@PathVariable("topicId") int topicId){
		Optional<Topic> data = topicRepository.findById(topicId);
		if(data.isPresent()) {
			Topic topic = data.get();
			List<Exam> results = new ArrayList<Exam>();
			for (Widget w: topic.getWidgets()) {
				if (w instanceof Exam) {
					results.add((Exam)w);
				}
			}
			return results;
		}
		return null;
		
	}
	
	@GetMapping("/api/exam/{AId}")
	public Exam findExamById(@PathVariable("AId") int id) {
		Optional<Exam> data = repository.findById(id);
		if (data.isPresent()) {
			return data.get();
		}
		return null;
	}
	
	@PostMapping("/api/topic/{topicId}/exam")
	public void createExamForTopic(@PathVariable("topicId") int topicId, @RequestBody List<Exam> newExams) {
		
		Optional<Topic> data = topicRepository.findById(topicId);
		if(data.isPresent()) {
			Topic topic = data.get();
			for(Exam exam: newExams) {
				
				exam.setTopic(topic);
			}
			repository.saveAll(newExams);
		}
		
	}
	
	@PutMapping("/api/exam/{AId}")
	public Exam updateWidget(@PathVariable("AId") int AId, @RequestBody Exam newExam ) {
		Optional<Exam> data = repository.findById(AId);
		if (data.isPresent()) {
			Exam exam = data.get();
			exam.setExamTitle(newExam.getExamTitle());
			exam.setDescription(newExam.getDescription());
			exam.setPoints(newExam.getPoints());
			repository.save(exam);
			return exam;
		}
		return null;
	}
	
	@DeleteMapping("/api/exam/{AId}")
	public void deleteAssignment(
	  @PathVariable("AId")
	    int AId) {
		Optional<Exam> data = repository.findById(AId);
		if(data.isPresent()) {
			data.get().getTopic().getLesson().getModule().getCourse().setModified(new Date());
		}
		repository.deleteById(AId);
	}
}