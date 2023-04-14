package com.DYShunyaev.LearningWeb.services;

import com.DYShunyaev.LearningWeb.models.Course;
import com.DYShunyaev.LearningWeb.models.Users;
import com.DYShunyaev.LearningWeb.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public List<Course> showCoursesByTeacherId(Long teacherId) {
        return courseRepository.findByTeacherId(teacherId);
    }

    public List<Course> showAllCourses() {
        return (List<Course>) courseRepository.findAll();
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
    public boolean existCourseById(Long id) {
        return courseRepository.existsById(id);
    }

    public Optional<Course> findCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public void subscribe(Users user, Course course) {
        course.getUsersSubs().add(user);
        courseRepository.save(course);
    }

    public void unsubscribe(Users user, Course course) {
        course.getUsersSubs().remove(user);
        courseRepository.save(course);
    }

//    public Set<Course> findCourseByUsersId(Long courseId) {
//        return courseRepository.findCourseByUsersId(courseId);
//    }
}
