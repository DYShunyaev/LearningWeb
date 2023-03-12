package com.DYShunyaev.LearningWeb.services;

import com.DYShunyaev.LearningWeb.models.Course;
import com.DYShunyaev.LearningWeb.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public void createNewCourse(Course course) {
        courseRepository.save(course);
    }

    public List<Course> showCoursesByUserId(Long userId) {
        return courseRepository.findByUserId(userId);
    }
}
