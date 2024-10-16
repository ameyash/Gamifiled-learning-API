package com.gamified.learning_platform.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gamified.learning_platform.Models.Course;
import com.gamified.learning_platform.Models.Quiz;
import com.gamified.learning_platform.Models.User;
import com.gamified.learning_platform.Models.UserCourse;
import com.gamified.learning_platform.Models.UserQuiz;
import com.gamified.learning_platform.Repositories.CourseLinkRepository;
import com.gamified.learning_platform.Repositories.CourseRepository;
import com.gamified.learning_platform.Repositories.UserCourseRepository;
import com.gamified.learning_platform.Repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserCourseRepository userCourseRepository;
	
	@Autowired
	private CourseLinkRepository courseLinkRepository;

	public List<Course> getAllCourses() {
		return courseRepository.findAll();
	}

	public Course getCourseById(long id) {

		return courseRepository.findById(id).orElse(new Course());
	}
	
	public List<Long> getUserCourseByUserId(String email) {
		Optional<User> userOptional = userRepository.findByEmail(email);
		List<Long> courseIds = new ArrayList<Long>();
		List<UserCourse> list = userCourseRepository.findByUserId(userOptional.get().getId());
		for(UserCourse i: list) {
			courseIds.add(i.getCourse().getId());
		}
		return courseIds;
	}
	
	public Long getCountAllEnrolledCourses(String email) {
		try {
			Optional<User> user = userRepository.findByEmail(email); 
			List<UserCourse> list = userCourseRepository.findByUserId(user.get().getId());
			return (long) list.size();
		}
		catch(Exception e) {
			return (long)0;
		}	
	}
	
	// Method to handle the logic when a user takes a quiz
	@Transactional
	public String takeCourse(String email, Long courseId) {
		// Fetch user and quiz entities
		Optional<User> userOptional = userRepository.findByEmail(email);
		Optional<Course> CourseOptional = courseRepository.findById(courseId);

		if (userOptional.isPresent() && CourseOptional.isPresent()) {
			User user = userOptional.get();
			Course course = CourseOptional.get();

			// Check if there's already a UserQuiz entry for this user and quiz
			Optional<UserCourse> userCourseOptional = userCourseRepository.findByUserAndCourse(user, course);

			UserCourse userCourse;

			if (userCourseOptional.isPresent()) {
				// If the entry exists, update the status
				userCourse = userCourseOptional.get();
				if (!userCourse.getStatus().equals("Enrolled")) {
					userCourse.setStatus("Enrolled");
					userCourseRepository.save(userCourse);
					return "Course status updated to 'Enrolled' for user: " + user.getUsername();
				} else {
					return "User has already Enrolled this Course.";
				}
			} else {
				// If no entry exists, create a new one and mark it as 'Completed'
				userCourse = new UserCourse();
				userCourse.setUser(user);
				userCourse.setCourse(course);
				userCourse.setStatus("Enrolled");
				userCourseRepository.save(userCourse);

				return "Course taken by user: " + user.getUsername() + ", status set to 'Enrolled'.";
			}
		}
		return "User or Coruse not found.";
	}
	
	// Method to check quiz status for a user
    public boolean checkCourseStatus(String email, Long courseId) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        Optional<Course> courseOptional = courseRepository.findById(courseId);

        if (userOptional.isPresent() && courseOptional.isPresent()) {
            User user = userOptional.get();
            Course course = courseOptional.get();

            // Check the status in UserQuiz
            Optional<UserCourse> userCourseOptional = userCourseRepository.findByUserAndCourse(user, course);

            if (userCourseOptional.isPresent()) {
                return true;
            } 
        
        }
        return false;
    }

	public String getCourseLinkByCourseId(long courseId) {
		try {
			return courseLinkRepository.getCourseLinkByCourseId(courseId).getLink();			
		}
		catch (Exception e) {}
		return null;
	}
}
