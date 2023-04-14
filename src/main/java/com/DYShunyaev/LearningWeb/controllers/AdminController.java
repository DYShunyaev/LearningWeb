package com.DYShunyaev.LearningWeb.controllers;

import com.DYShunyaev.LearningWeb.models.Course;
import com.DYShunyaev.LearningWeb.models.Role;
import com.DYShunyaev.LearningWeb.models.Users;
import com.DYShunyaev.LearningWeb.services.CourseService;
import com.DYShunyaev.LearningWeb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

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
    public String adminPage(@PathVariable(value = "id", required = false) long id, Model model) {
        if (!userService.existUserById(id)) {
            String message = "This admin not founded.";
            model.addAttribute("error", message);
            return "error";
        }
        Users user = userService.findUserById(id).orElseThrow();
        model.addAttribute("adminPage", user);

        List<Course> courseList = courseService.showCoursesByTeacherId(user.getId());
        model.addAttribute("courses", courseList);
        return "admin/adminPage";
    }

    @RequestMapping("/getUserAdminRole/{id}")
    public String setUserAdminRole(@PathVariable(name = "id") Long user_id) {
        Users user = userService.findUserById(user_id).orElseThrow();
        Set<Role> roles = user.getRoles();
        for (int i = 0; i < roles.size(); i++) {
            roles.remove(Role.USER);
        }
        roles.add(Role.ADMIN);
        user.setRoles(roles);
        userService.saveNewUser(user);

        return "redirect:/admin/main";
    }
    @RequestMapping("/removeAdminRole/{id}")
    public String removeAdminRole(@PathVariable(name = "id") Long user_id) {
        Users user = userService.findUserById(user_id).orElseThrow();
//        userService.deleteUserById(user_id);
//        user.setRoles(Collections.singleton(Role.USER));
//        user.setId(user_id);
//        userService.saveNewUser(user);
        Set<Role> roles = user.getRoles();
        for (int i = 0; i < roles.size(); i++) {
            roles.remove(Role.ADMIN);
        }
        roles.add(Role.USER);
        userService.saveNewUser(user);
        return "redirect:/admin/main";
    }
}
