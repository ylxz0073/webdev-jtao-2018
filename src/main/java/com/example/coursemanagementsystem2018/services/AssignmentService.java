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

import com.example.coursemanagementsystem2018.models.Assignment;
import com.example.coursemanagementsystem2018.models.Topic;
import com.example.coursemanagementsystem2018.models.Widget;
import com.example.coursemanagementsystem2018.repositories.AssignmentRepository;
import com.example.coursemanagementsystem2018.repositories.TopicRepository;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class AssignmentService {

	@Autowired
	AssignmentRepository repository;
	@Autowired
	TopicRepository topicRepository;
	
	@GetMapping("/api/assignment")
	public List<Assignment> findAllAssignments() {
		List<Assignment> results = (List<Assignment>)repository.findAll();
		
		return results;
	}
	
	@GetMapping("/api/topic/{topicId}/assignment")
	public List<Assignment> findAllAssignmentsForTopic(
			@PathVariable("topicId") int topicId){
		Optional<Topic> data = topicRepository.findById(topicId);
		if(data.isPresent()) {
			Topic topic = data.get();
			List<Assignment> results = new ArrayList<Assignment>();
			for (Widget w: topic.getWidgets()) {
				if (w instanceof Assignment) {
					results.add((Assignment)w);
				}
			}
			return results;
		}
		return null;
		
	}
	
	@GetMapping("/api/assignment/{AId}")
	public Assignment findAssignmentById(@PathVariable("AId") int id) {
		Optional<Assignment> data = repository.findById(id);
		if (data.isPresent()) {
			return data.get();
		}
		return null;
	}
	
	@PostMapping("/api/topic/{topicId}/assignment")
	public void createAssignmentsForTopic(@PathVariable("topicId") int topicId, @RequestBody List<Assignment> newAssignments) {
		
		Optional<Topic> data = topicRepository.findById(topicId);
		if(data.isPresent()) {
			Topic topic = data.get();
			for(Assignment assignment: newAssignments) {
				
				assignment.setTopic(topic);
			}
			repository.saveAll(newAssignments);
		}
		
	}
	
	@PutMapping("/api/assignment/{AId}")
	public Assignment updateWidget(@PathVariable("AId") int AId, @RequestBody Assignment newAssignment ) {
		Optional<Assignment> data = repository.findById(AId);
		if (data.isPresent()) {
			Assignment assignment = data.get();
			assignment.setAssignmentTitle(newAssignment.getAssignmentTitle());
			assignment.setParagraph(newAssignment.getParagraph());
			assignment.setPoints(newAssignment.getPoints());
			repository.save(assignment);
			return assignment;
		}
		return null;
	}
	
	@DeleteMapping("/api/assignment/{AId}")
	public void deleteAssignment(
	  @PathVariable("AId")
	    int AId) {
		Optional<Assignment> data = repository.findById(AId);
		if(data.isPresent()) {
			data.get().getTopic().getLesson().getModule().getCourse().setModified(new Date());
		}
		repository.deleteById(AId);
	}
}
