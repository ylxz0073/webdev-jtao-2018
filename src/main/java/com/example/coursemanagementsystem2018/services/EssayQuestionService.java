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
import com.example.coursemanagementsystem2018.repositories.EssayQuestionRepository;
import com.example.coursemanagementsystem2018.repositories.ExamRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class EssayQuestionService {
	@Autowired
	EssayQuestionRepository repository;
	@Autowired
	ExamRepository examRepository;
	
	@GetMapping("/api/essay")
	public List<EssayQuestion> findAllEssayQuestionss() {
		List<EssayQuestion> results = (List<EssayQuestion>)repository.findAll();
		
		return results;
	}
	
	@GetMapping("/api/exam/{EId}/essay")
	public List<EssayQuestion> findAllEssayQuestionsForExam(
			@PathVariable("EId") int examId){
		Optional<Exam> data = examRepository.findById(examId);
		if(data.isPresent()) {
			Exam exam = data.get();
			List<EssayQuestion> results = new ArrayList<EssayQuestion>();
			for (BaseExamQuestion q: exam.getQuestions()) {
					if (q instanceof EssayQuestion ) {
						results.add((EssayQuestion)q);
					}
					
				
			}
			return results;
		}
		return null;
		
	}
	
	@GetMapping("/api/essay/{QId}")
	public EssayQuestion findEssayQuestionById(@PathVariable("QId") int id) {
		Optional<EssayQuestion> data = repository.findById(id);
		if (data.isPresent()) {
			return data.get();
		}
		return null;
	}
	
	@PostMapping("/api/exam/{EId}/essay")
	public void createEssayQuestionForExam(@PathVariable("EId") int examId, @RequestBody List<EssayQuestion> newQuestions) {
		
		Optional<Exam> data = examRepository.findById(examId);
		if(data.isPresent()) {
			Exam exam = data.get();
			for(EssayQuestion q: newQuestions) {
				
				q.setExam(exam);
				q.setType("ES");
			}
			repository.saveAll(newQuestions);
		}
		
	}
	
	@PutMapping("/api/essay/{QId}")
	public EssayQuestion updateEssayQuestion(@PathVariable("QId") int QId, @RequestBody EssayQuestion newQuestion ) {
		Optional<EssayQuestion> data = repository.findById(QId);
		if (data.isPresent()) {
			EssayQuestion question = data.get();
			question.setQuestionTitle(newQuestion.getQuestionTitle());
			question.setDescription(newQuestion.getDescription());
			question.setPoints(newQuestion.getPoints());
			repository.save(question);
			return question;
		}
		return null;
	}
	
	@DeleteMapping("/api/essay/{QId}")
	public void deleteEssayQuestion(
	  @PathVariable("QId")
	    int QId) {
		Optional<EssayQuestion> data = repository.findById(QId);
		if(data.isPresent()) {
			data.get().getExam().getTopic().getLesson().getModule().getCourse().setModified(new Date());
		}
		repository.deleteById(QId);
	}

}
