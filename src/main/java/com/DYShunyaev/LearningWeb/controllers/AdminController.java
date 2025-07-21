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
    public String adminMain(Model model){

        Users admin = userService.getAuthorizationUser();
        model.addAttribute("adminPage", admin);

        List<Users> users = userService.findAllUsers();
        model.addAttribute("getAll", users);

        List<Course> courses = courseService.showAllCourses();
        model.addAttribute("courses", courses);
        return "admin/adminMain";
    }

    @RequestMapping("/adminPage/{id}")
    public String adminPage(@PathVariable(value = "id", required = false) long id, Model model) {
        if (userService.existUserById(id)) {
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

    @RequestMapping("/updateActive/{id}")
    public String updateActiveUser(@PathVariable(name = "id") Long userId) {
        Users user = userService.findUserById(userId).orElseThrow();
        user.setActive(!user.isActive());
        userService.saveNewUser(user);

        return "redirect:/admin/main";
    }
    @RequestMapping("/getUserRole/{role}/{id}")
    public String setRoleFromUser(@PathVariable(name = "id") Long user_id,
                                   @PathVariable(name = "role") String role) {
        Users user = userService.findUserById(user_id).orElseThrow();
        Set<Role> roles = user.getRoles();
        for (int i = 0; i < roles.size(); i++) {
            roles.remove(Role.USER);
        }
        if (role.equals("Admin")) roles.add(Role.ADMIN);
        else roles.add(Role.TEACHER);
        user.setRoles(roles);
        userService.saveNewUser(user);

        return "redirect:/admin/main";
    }
    @RequestMapping("/removeRole/{role}/{id}")
    public String removeRoleFromUser(@PathVariable(name = "id") Long user_id,
                                  @PathVariable(name = "role") String role) {
        Users user = userService.findUserById(user_id).orElseThrow();
        Set<Role> roles = user.getRoles();
        for (int i = 0; i < roles.size(); i++) {
            if (role.equals("Admin"))roles.remove(Role.ADMIN);
            else roles.remove(Role.TEACHER);
        }
        roles.add(Role.USER);
        userService.saveNewUser(user);
        return "redirect:/admin/main";
    }

    @RequestMapping("/deleteUser/{user_id}")
    public String deleteUser(@PathVariable(name = "user_id") Long userId) {
        userService.deleteUserById(userId);
        return "redirect:/admin/main";
    }

    @RequestMapping("/deleteCourse/{course_id}")
    public String deleteCourse(@PathVariable(name = "course_id") Long courseId) {
        courseService.deleteCourse(courseId);
        return "redirect:/admin/main";
    }
}
