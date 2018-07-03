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
import com.example.coursemanagementsystem2018.models.FillInTheBlankQuestion;
import com.example.coursemanagementsystem2018.repositories.ExamRepository;
import com.example.coursemanagementsystem2018.repositories.FillInTheBlankRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class FillInTheBlankQuestionService {
	@Autowired
	FillInTheBlankRepository repository;
	@Autowired
	ExamRepository examRepository;
	
	@GetMapping("/api/blanks")
	public List<FillInTheBlankQuestion> findAllFillInTheBlankQuestions() {
		List<FillInTheBlankQuestion> results = (List<FillInTheBlankQuestion>)repository.findAll();
		
		return results;
	}
	
	@GetMapping("/api/exam/{EId}/blanks")
	public List<FillInTheBlankQuestion> findAllFillInTheBlankQuestionsForExam(
			@PathVariable("EId") int examId){
		Optional<Exam> data = examRepository.findById(examId);
		if(data.isPresent()) {
			Exam exam = data.get();
			List<FillInTheBlankQuestion> results = new ArrayList<FillInTheBlankQuestion>();
			for (BaseExamQuestion q: exam.getQuestions()) {
					if (q instanceof FillInTheBlankQuestion ) {
						results.add((FillInTheBlankQuestion)q);
					}
					
				
			}
			return results;
		}
		return null;
		
	}
	
	@GetMapping("/api/blanks/{QId}")
	public FillInTheBlankQuestion findFillInTheBlankQuestionById(@PathVariable("QId") int id) {
		Optional<FillInTheBlankQuestion> data = repository.findById(id);
		if (data.isPresent()) {
			return data.get();
		}
		return null;
	}
	
	@PostMapping("/api/exam/{EId}/blanks")
	public void createFillInTheBlankQuestionForExam(@PathVariable("EId") int examId, @RequestBody List<FillInTheBlankQuestion> newQuestions) {
		
		Optional<Exam> data = examRepository.findById(examId);
		if(data.isPresent()) {
			Exam exam = data.get();
			for(FillInTheBlankQuestion q: newQuestions) {
				
				q.setExam(exam);
				q.setType("FB");
			}
			repository.saveAll(newQuestions);
		}
		
	}
	
	@PutMapping("/api/blanks/{QId}")
	public FillInTheBlankQuestion updateFillInTheBlankQuestion(@PathVariable("QId") int QId, @RequestBody FillInTheBlankQuestion newQuestion ) {
		Optional<FillInTheBlankQuestion> data = repository.findById(QId);
		if (data.isPresent()) {
			FillInTheBlankQuestion question = data.get();
			question.setQuestionTitle(newQuestion.getQuestionTitle());
			question.setDescription(newQuestion.getDescription());
			question.setPoints(newQuestion.getPoints());
			question.setVariables(newQuestion.getVariables());
			repository.save(question);
			return question;
		}
		return null;
	}
	
	@DeleteMapping("/api/blanks/{QId}")
	public void deleteFillInTheBlankQuestion(
	  @PathVariable("QId")
	    int QId) {
		Optional<FillInTheBlankQuestion> data = repository.findById(QId);
		if(data.isPresent()) {
			data.get().getExam().getTopic().getLesson().getModule().getCourse().setModified(new Date());
		}
		repository.deleteById(QId);
	}

}

