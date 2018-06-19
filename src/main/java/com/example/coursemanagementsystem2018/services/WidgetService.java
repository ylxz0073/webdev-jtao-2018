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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.example.coursemanagementsystem2018.models.Topic;
import com.example.coursemanagementsystem2018.models.Widget;
import com.example.coursemanagementsystem2018.repositories.TopicRepository;
import com.example.coursemanagementsystem2018.repositories.WidgetRepository;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class WidgetService {
	@Autowired
	WidgetRepository repository;
	@Autowired
	TopicRepository topicRepository;
	
	@PostMapping("/api/widget/save")
	public void saveAllWidgets(@RequestBody List<Widget> widgets) {
		repository.deleteAll();
		for(Widget widget: widgets) {
			repository.save(widget);
			
		}
	}
	
	@PostMapping("/api/topic/{topicId}/widget")
	public void saveWidgetForTopic(@PathVariable("topicId") int topicId, @RequestBody List<Widget> newWidgets) {
		
		Optional<Topic> data = topicRepository.findById(topicId);
		if(data.isPresent()) {
			Topic topic = data.get();
			List<Widget> widgets = topic.getWidgets();
			repository.deleteAll(widgets);
			for(Widget widget: newWidgets) {
				widget.setTopic(topic);
			}
			repository.saveAll(newWidgets);
		}
		
		
		
	}
	
	
	@GetMapping("/api/widgets")
	public List<Widget> findAllWidgets() {
		List<Widget> results = (List<Widget>)repository.findAll();
		
		return results;
	}
	
	@GetMapping("/api/topic/{topicId}/widget")
	public List<Widget> findAllWidgetsForTopic(
			@PathVariable("topicId") int topicId){
		Optional<Topic> data = topicRepository.findById(topicId);
		if(data.isPresent()) {
			Topic topic = data.get();
			return topic.getWidgets();
		}
		return null;
		
	}
	
	
	@GetMapping("/api/widget/{widgetId}")
	public Widget findWidgetById(@PathVariable("widgetId") int id) {
		Optional<Widget> data = repository.findById(id);
		if (data.isPresent()) {
			return data.get();
		}
		return null;
	}
	
	@PutMapping("/api/widget/{widgetId}")
	public Widget updateWidget(@PathVariable("widgetId") int widgetId, @RequestBody Widget newWidget ) {
		Optional<Widget> data = repository.findById(widgetId);
		if (data.isPresent()) {
			Widget widget = data.get();
			widget.setName(newWidget.getName());
			widget.setText(newWidget.getText());
			widget.setWidgetType(newWidget.getWidgetType());
			repository.save(widget);
			return widget;
		}
		return null;
	}
	
	
	@DeleteMapping("/api/widget/{widgetId}")
	public void deleteWidget(
	  @PathVariable("widgetId")
	    int widgetId) {
		Optional<Widget> data = repository.findById(widgetId);
		if(data.isPresent()) {
			data.get().getTopic().getLesson().getModule().getCourse().setModified(new Date());
		}
		repository.deleteById(widgetId);
	}
}
