package com.DYShunyaev.LearningWeb.controllers;

import com.DYShunyaev.LearningWeb.models.Course;
import com.DYShunyaev.LearningWeb.models.Role;
import com.DYShunyaev.LearningWeb.models.Users;
import com.DYShunyaev.LearningWeb.services.CourseService;
import com.DYShunyaev.LearningWeb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
public class MainController {

    private final UserService userService;
    private final CourseService courseService;

    public MainController() {
        courseService = null;
        userService = null;
    }

    @Autowired
    public MainController(UserService userService, CourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    @RequestMapping("/")
    public String mainView(Model model) {
        List<Course> courseList = courseService.showAllCourses();
        model.addAttribute("courses", courseList);
        Users users = userService.getAuthorizationUser();
        if (users.getRoles().stream().anyMatch(role -> role == Role.ADMIN)) {
            model.addAttribute("adminPage", users);
            return "redirect:/admin/main";
        }
        model.addAttribute("authUser", users);
        return "mainView";
    }

    @RequestMapping("/allUsers")
    public String allUsers(Model model) {
        model.addAttribute("authUser", userService.getAuthorizationUser());

        List<Users> usersList = userService.findAllUsers();
        model.addAttribute("allUsers", usersList);

        return "allUsers";
    }
}
