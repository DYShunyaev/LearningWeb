package com.DYShunyaev.LearningWeb.controllers;

import com.DYShunyaev.LearningWeb.models.Comments;
import com.DYShunyaev.LearningWeb.services.CommentsService;
import com.DYShunyaev.LearningWeb.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/comment")
@Controller
public class CommentsController {

    private final CommentsService commentsService;
    private final CourseService courseService;

    @Autowired
    public CommentsController(CommentsService commentsService, CourseService courseService) {
        this.commentsService = commentsService;
        this.courseService = courseService;
    }

    @RequestMapping("/saveComment/{userName}/{userId}/{courseId}")
    public String saveNewComment(@PathVariable(name = "courseId") Long courseId,
                                 @PathVariable(name = "userName") String userName,
                                 @PathVariable(name = "userId") Long userId,
                                 @RequestParam(name = "commentaryText") String commentaryText) {

        Comments comment = new Comments(userName, userId, commentaryText);
        comment.setWithCourse(courseService.findCourseById(courseId).orElseThrow());
        commentsService.setNewComment(comment);

        return "redirect:/course/" + courseId;
    }

    @RequestMapping("/deleteComment/{commentId}")
    public String deleteComment(@PathVariable(name = "commentId") Long commentId) {
        Long courseId = commentsService.findCommentById(commentId).get().getCourseId();
        commentsService.deleteComment(commentId);

        return "redirect:/course/" + courseId;
    }
}
