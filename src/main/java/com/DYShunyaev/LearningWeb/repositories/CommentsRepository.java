package com.DYShunyaev.LearningWeb.repositories;

import com.DYShunyaev.LearningWeb.models.Comments;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentsRepository extends CrudRepository<Comments, Long> {
    List<Comments> findByCourseId(Long courseId);
}
