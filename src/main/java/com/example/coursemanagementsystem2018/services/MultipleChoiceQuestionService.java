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
import com.example.coursemanagementsystem2018.models.MultipleChoiceQuestion;
import com.example.coursemanagementsystem2018.repositories.ExamRepository;
import com.example.coursemanagementsystem2018.repositories.MultipleChoiceQuestionRepository;
import com.example.coursemanagementsystem2018.repositories.QuestionRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class MultipleChoiceQuestionService {
	@Autowired
	MultipleChoiceQuestionRepository repository;
	@Autowired
	ExamRepository examRepository;
	
	@GetMapping("/api/multipleChoiceQuestion")
	public List<MultipleChoiceQuestion> findAllQuestionss() {
		List<MultipleChoiceQuestion> results = (List<MultipleChoiceQuestion>)repository.findAll();
		
		return results;
	}
	
	@GetMapping("/api/exam/{EId}/multipleChoiceQuestion")
	public List<MultipleChoiceQuestion> findAllQuestionsForExam(
			@PathVariable("EId") int examId){
		Optional<Exam> data = examRepository.findById(examId);
		if(data.isPresent()) {
			Exam exam = data.get();
			List<MultipleChoiceQuestion> results = new ArrayList<MultipleChoiceQuestion>();
			for (BaseExamQuestion q: exam.getQuestions()) {
					if (q instanceof MultipleChoiceQuestion) {
						results.add((MultipleChoiceQuestion)q);
					}
					
				
			}
			return results;
		}
		return null;
		
	}
	
	@GetMapping("/api/multipleChoiceQuestion/{QId}")
	public MultipleChoiceQuestion findMultipleChoiceQuestionById(@PathVariable("QId") int id) {
		Optional<MultipleChoiceQuestion> data = repository.findById(id);
		if (data.isPresent()) {
			return data.get();
		}
		return null;
	}
	
	@PostMapping("/api/exam/{EId}/multipleChoiceQuestion")
	public void createQuestionForExam(@PathVariable("EId") int examId, @RequestBody List<MultipleChoiceQuestion> newQuestions) {
		
		Optional<Exam> data = examRepository.findById(examId);
		if(data.isPresent()) {
			Exam exam = data.get();
			for(MultipleChoiceQuestion q: newQuestions) {
				
				q.setExam(exam);
				q.setType("MC");
			}
			repository.saveAll(newQuestions);
		}
		
	}
	
	@PutMapping("/api/multipleChoiceQuestion/{QId}")
	public MultipleChoiceQuestion updateQuestion(@PathVariable("QId") int QId, @RequestBody MultipleChoiceQuestion newQuestion ) {
		Optional<MultipleChoiceQuestion> data = repository.findById(QId);
		if (data.isPresent()) {
			MultipleChoiceQuestion question = data.get();
			question.setQuestionTitle(newQuestion.getQuestionTitle());
			question.setDescription(newQuestion.getDescription());
			question.setPoints(newQuestion.getPoints());
			question.setCorrectChoice(newQuestion.getCorrectChoice());
			question.setChoices(newQuestion.getChoices());
			repository.save(question);
			return question;
		}
		return null;
	}
	
	@DeleteMapping("/api/multipleChoiceQuestion/{QId}")
	public void deleteAssignment(
	  @PathVariable("QId")
	    int QId) {
		Optional<MultipleChoiceQuestion> data = repository.findById(QId);
		if(data.isPresent()) {
			data.get().getExam().getTopic().getLesson().getModule().getCourse().setModified(new Date());
		}
		repository.deleteById(QId);
	}

}
