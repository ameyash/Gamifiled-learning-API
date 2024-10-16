package com.gamified.learning_platform.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamified.learning_platform.Models.Course;
import com.gamified.learning_platform.Models.User;
import com.gamified.learning_platform.Models.UserCourse;

public interface UserCourseRepository extends JpaRepository<UserCourse, Long> {
	// Fetch UserQuiz by userId and quizId
	Optional<UserCourse> findByUserAndCourse(User user, Course course);
	
	UserCourse findByUserIdAndCourseId(Long userId, Long courseId);
	
	UserCourse findByUserEmailAndCourseId(String email, Long courseId);

	// Fetch UserQuiz by userId
	List<UserCourse> findByUserId(Long userId);
}