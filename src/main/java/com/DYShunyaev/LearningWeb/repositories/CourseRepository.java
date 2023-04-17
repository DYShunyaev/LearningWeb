package com.DYShunyaev.LearningWeb.repositories;

import com.DYShunyaev.LearningWeb.models.Course;
import com.DYShunyaev.LearningWeb.models.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseRepository extends CrudRepository<Course, Long> {
    List<Course> findByTeacherId(Long teacherId);
//    Set<Course> findCourseByUsersId(Long userId);
    void deleteByTeacherId(Long teacherId);
}
