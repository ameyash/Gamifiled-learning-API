package com.gamified.learning_platform.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamified.learning_platform.Models.CourseLinks;

public interface CourseLinkRepository extends JpaRepository<CourseLinks, Long>{
	//Fetch by courseId;
	CourseLinks getCourseLinkByCourseId(Long courseId);
}
