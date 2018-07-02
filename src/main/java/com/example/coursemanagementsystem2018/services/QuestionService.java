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

import com.example.coursemanagementsystem2018.models.BaseExamQuestion;
import com.example.coursemanagementsystem2018.models.Exam;
import com.example.coursemanagementsystem2018.models.Topic;
import com.example.coursemanagementsystem2018.models.Widget;
import com.example.coursemanagementsystem2018.repositories.ExamRepository;
import com.example.coursemanagementsystem2018.repositories.QuestionRepository;
import com.example.coursemanagementsystem2018.repositories.TopicRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class QuestionService {
	@Autowired
	QuestionRepository repository;
	@Autowired
	ExamRepository examRepository;
	
	@GetMapping("/api/question")
	public List<BaseExamQuestion> findAllQuestionss() {
		List<BaseExamQuestion> results = (List<BaseExamQuestion>)repository.findAll();
		
		return results;
	}
	
	@GetMapping("/api/exam/{EId}/question")
	public List<BaseExamQuestion> findAllQuestionsForExam(
			@PathVariable("EId") int examId){
		Optional<Exam> data = examRepository.findById(examId);
		if(data.isPresent()) {
			Exam exam = data.get();
			List<BaseExamQuestion> results = new ArrayList<BaseExamQuestion>();
			for (BaseExamQuestion q: exam.getQuestions()) {
				
					results.add(q);
				
			}
			return results;
		}
		return null;
		
	}
	
	@GetMapping("/api/question/{QId}")
	public BaseExamQuestion findQuestionById(@PathVariable("QId") int id) {
		Optional<BaseExamQuestion> data = repository.findById(id);
		if (data.isPresent()) {
			return data.get();
		}
		return null;
	}
	
	@PostMapping("/api/exam/{EId}/question")
	public void createQuestionForExam(@PathVariable("EId") int examId, @RequestBody List<BaseExamQuestion> newQuestions) {
		
		Optional<Exam> data = examRepository.findById(examId);
		if(data.isPresent()) {
			Exam exam = data.get();
			for(BaseExamQuestion q: newQuestions) {
				
				q.setExam(exam);
			}
			repository.saveAll(newQuestions);
		}
		
	}
	
	@PutMapping("/api/question/{QId}")
	public BaseExamQuestion updateQuestion(@PathVariable("QId") int QId, @RequestBody BaseExamQuestion newQuestion ) {
		Optional<BaseExamQuestion> data = repository.findById(QId);
		if (data.isPresent()) {
			BaseExamQuestion question = data.get();
			question.setQuestionTitle(newQuestion.getQuestionTitle());
			question.setDescription(newQuestion.getDescription());
			question.setPoints(newQuestion.getPoints());
			repository.save(question);
			return question;
		}
		return null;
	}
	
	@DeleteMapping("/api/question/{QId}")
	public void deleteAssignment(
	  @PathVariable("QId")
	    int QId) {
		Optional<BaseExamQuestion> data = repository.findById(QId);
		if(data.isPresent()) {
			data.get().getExam().getTopic().getLesson().getModule().getCourse().setModified(new Date());
		}
		repository.deleteById(QId);
	}

}
