package com.DYShunyaev.LearningWeb.controllers;

import com.DYShunyaev.LearningWeb.models.Role;
import com.DYShunyaev.LearningWeb.models.Users;
import com.DYShunyaev.LearningWeb.services.CourseService;
import com.DYShunyaev.LearningWeb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.Collections;

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
        Users users = userService.getAuthorizationUser();
        if (users.getRoles().stream().anyMatch(role -> role == Role.ADMIN)) {
            model.addAttribute("adminPage", users);
            return "redirect:/admin/main";
        }
        model.addAttribute("userPage", users);
        return "mainView";
    }

}
