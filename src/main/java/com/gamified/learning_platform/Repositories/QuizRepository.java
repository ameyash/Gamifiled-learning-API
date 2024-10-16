package com.gamified.learning_platform.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamified.learning_platform.Models.Quiz;
import com.gamified.learning_platform.Models.QuizQuestion;


public interface QuizRepository extends JpaRepository<Quiz, Long>{
}
