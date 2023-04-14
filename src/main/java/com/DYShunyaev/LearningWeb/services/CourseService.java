package com.DYShunyaev.LearningWeb.services;

import com.DYShunyaev.LearningWeb.models.Course;
import com.DYShunyaev.LearningWeb.models.Users;
import com.DYShunyaev.LearningWeb.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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

    public List<Course> showCoursesByUserId(Long userId) {
        return courseRepository.findByTeacherId(userId);
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

//    public void subscribe(Users user, Course course) {
//        course.getUsersSubs().add(user);
////        List<Users> list = course.getUsersSubs();
////        System.out.println(Arrays.toString(new List[]{list}));
//        courseRepository.save(course);
//    }
//
//    public void unsubscribe(Users user, Course course) {
//        course.getUsersSubs().remove(user);
//        courseRepository.save(course);
//    }
//
//    public Set<Course> findUsersByCourseId(Long courseId) {
//        return courseRepository.findCourseByUserId(courseId);
//    }
}
