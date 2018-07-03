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
import com.example.coursemanagementsystem2018.models.EssayQuestion;
import com.example.coursemanagementsystem2018.models.Exam;
import com.example.coursemanagementsystem2018.models.TrueOrFalseQuestion;
import com.example.coursemanagementsystem2018.repositories.TrueOrFalseQuestionRepository;
import com.example.coursemanagementsystem2018.repositories.ExamRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class TrueOrFalseQuestionService {
	@Autowired
	TrueOrFalseQuestionRepository repository;
	@Autowired
	ExamRepository examRepository;
	
	@GetMapping("/api/truefalse")
	public List<TrueOrFalseQuestion> findAllTrueOrFalseQuestionss() {
		List<TrueOrFalseQuestion> results = (List<TrueOrFalseQuestion>)repository.findAll();
		
		return results;
	}
	
	@GetMapping("/api/exam/{EId}/truefalse")
	public List<TrueOrFalseQuestion> findAllTrueOrFalseQuestionsForExam(
			@PathVariable("EId") int examId){
		Optional<Exam> data = examRepository.findById(examId);
		if(data.isPresent()) {
			Exam exam = data.get();
			List<TrueOrFalseQuestion> results = new ArrayList<TrueOrFalseQuestion>();
			for (BaseExamQuestion q: exam.getQuestions()) {
					if (q instanceof TrueOrFalseQuestion ) {
						results.add((TrueOrFalseQuestion)q);
					}
					
				
			}
			return results;
		}
		return null;
		
	}
	
	@GetMapping("/api/truefalse/{QId}")
	public TrueOrFalseQuestion findTrueOrFalseQuestionById(@PathVariable("QId") int id) {
		Optional<TrueOrFalseQuestion> data = repository.findById(id);
		if (data.isPresent()) {
			return data.get();
		}
		return null;
	}
	
	@PostMapping("/api/exam/{EId}/truefalse")
	public void createTrueOrFalseQuestionForExam(@PathVariable("EId") int examId, @RequestBody List<TrueOrFalseQuestion> newQuestions) {
		
		Optional<Exam> data = examRepository.findById(examId);
		if(data.isPresent()) {
			Exam exam = data.get();
			for(TrueOrFalseQuestion q: newQuestions) {
				
				q.setExam(exam);
				q.setType("TF");
			}
			repository.saveAll(newQuestions);
		}
		
	}
	
	@PutMapping("/api/truefalse/{QId}")
	public TrueOrFalseQuestion updateTrueOrFalseQuestion(@PathVariable("QId") int QId, @RequestBody TrueOrFalseQuestion newQuestion ) {
		Optional<TrueOrFalseQuestion> data = repository.findById(QId);
		if (data.isPresent()) {
			TrueOrFalseQuestion question = data.get();
			question.setQuestionTitle(newQuestion.getQuestionTitle());
			question.setDescription(newQuestion.getDescription());
			question.setPoints(newQuestion.getPoints());
			question.setIsTrue(newQuestion.getIsTrue());
			repository.save(question);
			return question;
		}
		return null;
	}
	
	@DeleteMapping("/api/truefalse/{QId}")
	public void deleteEssayQuestion(
	  @PathVariable("QId")
	    int QId) {
		Optional<TrueOrFalseQuestion> data = repository.findById(QId);
		if(data.isPresent()) {
			data.get().getExam().getTopic().getLesson().getModule().getCourse().setModified(new Date());
		}
		repository.deleteById(QId);
	}

}
