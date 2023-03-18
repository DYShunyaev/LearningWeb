package com.DYShunyaev.LearningWeb.controllers;

import com.DYShunyaev.LearningWeb.models.Course;
import com.DYShunyaev.LearningWeb.models.Users;
import com.DYShunyaev.LearningWeb.services.CourseService;
import com.DYShunyaev.LearningWeb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final CourseService courseService;

    public UserController() {
        userService = null;
        courseService = null;
    }

    @Autowired
    public UserController(UserService userService, CourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    @RequestMapping("/userPage/{id}")
    public String userPage(@PathVariable(value = "id", required = false) long id, Model model,
                           Model courseModel) {
        if (!userService.existUserById(id)) {
            String message = "This user not founded.";
            model.addAttribute("error", message);
            return "error";
        }
        Users user = userService.findUserById(id).orElseThrow();
        model.addAttribute("userPage", user);

        List<Course> courseList = courseService.showCoursesByUserId(user.getId());
        courseModel.addAttribute("courses", courseList);
        return "userPage";
    }
}
