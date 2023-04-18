package com.DYShunyaev.LearningWeb.services;

import com.DYShunyaev.LearningWeb.models.Comments;
import com.DYShunyaev.LearningWeb.repositories.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentsService {

    private final CommentsRepository commentsRepository;

    @Autowired
    public CommentsService(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    public void setNewComment(Comments comment) {
        commentsRepository.save(comment);
    }

    public List<Comments> getCommentsByCourseId(Long courseId) {
        return commentsRepository.findByCourseId(courseId);
    }

    public void deleteComment(Long commentId) {
        commentsRepository.deleteById(commentId);
    }

    public Optional<Comments> findCommentById(Long commentId) {
        return commentsRepository.findById(commentId);
    }
}
