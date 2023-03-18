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
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final CourseService courseService;

    @Autowired
    public AdminController(UserService userService, CourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    @RequestMapping("/main")
    public String adminMain(Model model, Model getAllUsers){
        Users admin = userService.getAuthorizationUser();
        model.addAttribute("adminPage", admin);
        List<Users> users = userService.findAllUsers();
        getAllUsers.addAttribute("getAll", users);
        return "admin/adminMain";
    }

    @RequestMapping("/adminPage/{id}")
    public String adminPage(@PathVariable(value = "id", required = false) long id, Model model,
                           Model courseModel) {
        if (!userService.existUserById(id)) {
            String message = "This admin not founded.";
            model.addAttribute("error", message);
            return "error";
        }
        Users user = userService.findUserById(id).orElseThrow();
        model.addAttribute("adminPage", user);

        List<Course> courseList = courseService.showCoursesByUserId(user.getId());
        courseModel.addAttribute("courses", courseList);
        return "admin/adminPage";
    }
}
