package com.DYShunyaev.LearningWeb.repositories;

import com.DYShunyaev.LearningWeb.models.Course;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseRepository extends CrudRepository<Course, Long> {
    List<Course> showCursesByUserId(Long user_id);
}
