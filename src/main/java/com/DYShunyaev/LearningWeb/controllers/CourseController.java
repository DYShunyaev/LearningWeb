package com.DYShunyaev.LearningWeb.controllers;

import com.DYShunyaev.LearningWeb.models.Course;
import com.DYShunyaev.LearningWeb.models.Users;
import com.DYShunyaev.LearningWeb.services.CourseService;
import com.DYShunyaev.LearningWeb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/course")
@Controller
public class CourseController {
    private final CourseService courseService;
    private final UserService userService;

    @Autowired
    public CourseController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @Value("${upload.path}")
    private String uploadPath;

    @RequestMapping("/byUser/{id}/createCourse")
    public String createCourse(@PathVariable(name = "id") long userId,
                               Model model, Model userModel) {
        Users user = userService.findUserById(userId).orElseThrow();
        userModel.addAttribute("userPage", user);
        Course course = new Course();
        model.addAttribute("course", course);
        return "createCourse";
    }

    @PostMapping("/byUser/{id}/createCourse")
    public String saveCourse(@PathVariable(name = "id") long user_id,
                             @RequestParam(name = "courseName") String courseName,
                             @RequestParam(name = "coursePreview") String coursePreview,
                             @RequestParam(name = "courseContent") String courseContent) {
        Course course = new Course(courseName, coursePreview, courseContent);
        Users user = userService.findUserById(user_id).orElseThrow();
        course.setUser(user);
        courseService.createNewCourse(course);
        return "redirect:/userPage/" + user.getId();
    }
}
