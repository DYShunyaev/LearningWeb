package com.DYShunyaev.LearningWeb.services;

import com.DYShunyaev.LearningWeb.models.Course;
import com.DYShunyaev.LearningWeb.models.Users;
import com.DYShunyaev.LearningWeb.repositories.CommentsRepository;
import com.DYShunyaev.LearningWeb.repositories.CourseRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CommentsRepository commentsRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, CommentsRepository commentsRepository) {
        this.courseRepository = courseRepository;
        this.commentsRepository = commentsRepository;
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
        Course course = courseRepository.findById(id).orElseThrow();
//        commentsRepository.deleteAllByCourseId(course.getCommentsList());
        commentsRepository.deleteAll(course.getCommentsList());
        File file = new File("usersPhoto/courses/" + course.getCourseName());
        try {
            FileUtils.deleteDirectory(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

}
