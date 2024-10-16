package com.gamified.learning_platform.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gamified.learning_platform.Models.Quiz;
import com.gamified.learning_platform.Models.QuizQuestion;
import com.gamified.learning_platform.Models.UserQuiz;
import com.gamified.learning_platform.Services.QuizService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class QuizController {
	
	@Autowired
	private QuizService quizService;
	
	// Fetch all quizzes
	@GetMapping("/quizzes")
	public List<Quiz> getAllQuiz(){
		return quizService.getAllQuiz();
	}
	
	@GetMapping("/quiz/{quizId}")
	public Quiz getQuizById(@PathVariable Long quizId){
		return quizService.getQuizById(quizId);
	}
	
	// Fetch questions for a specific quiz by quizId
	@GetMapping("/quizzes/{quizId}")
    public List<QuizQuestion> getQuestionsByQuizId(@PathVariable Long quizId) {
        return quizService.getQuestionsByQuizId(quizId);
    }
	
	//Fetch Quiz Status for specific user by userId
	@GetMapping("/quizzes/all/{email}")
	public List<Long> getUserQuizByUserId(@PathVariable String email){
		return quizService.getUserQuizByUserId(email);
	}
	
	@GetMapping("/quizzes/count/{email}")
	public Long getAllEnrolledCourseQuiz(@PathVariable String email) {
		return quizService.getCountAllEnrolledQuizzes(email);
	}
	
    // User takes a quiz (updates the status to 'Completed' for that user)
    @PostMapping("/quizzes/{quizId}/user/{email}/take")
    public String takeQuiz(@PathVariable String email, @PathVariable Long quizId) {
        return quizService.takeQuiz(email, quizId);
    }

    // Check quiz status for a specific user
    @GetMapping("/quizzes/{quizId}/user/{email}/status")
    public boolean checkQuizStatus(@PathVariable String email, @PathVariable Long quizId) {
        return quizService.checkQuizStatus(email, quizId);
    }
}
