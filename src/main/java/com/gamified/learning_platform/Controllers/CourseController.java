package com.gamified.learning_platform.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamified.learning_platform.Models.Course;
import com.gamified.learning_platform.Services.CourseService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CourseController {

	@Autowired
	private CourseService courseService;
	
	@GetMapping("/courses")
	public List<Course> getAllCourse(){
		return courseService.getAllCourses();
	}
	
	@GetMapping("/course/{courseId}")
	public Course getCourseById(@PathVariable long courseId){
		return courseService.getCourseById(courseId);
	}
	
	@GetMapping("course/all/{email}")
	public List<Long> getCourseEnrolled(@PathVariable String email){
		return courseService.getUserCourseByUserId(email);
	}
	
	@GetMapping("/course/count/{email}")
	public Long getAllEnrolledCourseCount(@PathVariable String email) {
		return courseService.getCountAllEnrolledCourses(email);
	}
	
	// User takes a quiz (updates the status to 'Completed' for that user)
    @PostMapping("/course/{courseId}/user/{email}/take")
    public String takeCourse(@PathVariable String email, @PathVariable Long courseId) {
        return courseService.takeCourse(email, courseId);
    }

    // Check quiz status for a specific user
    @GetMapping("/course/{courseId}/user/{email}/status")
    public boolean checkCourseStatus(@PathVariable String email, @PathVariable Long courseId) {
        return courseService.checkCourseStatus(email, courseId);
    }
    
    //Get Coruse Link by ID
    //@PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("course/play/{courseId}")
    public String getCourseLinkByCourseId(@PathVariable long courseId) {
    	return courseService.getCourseLinkByCourseId(courseId);
    }
}

