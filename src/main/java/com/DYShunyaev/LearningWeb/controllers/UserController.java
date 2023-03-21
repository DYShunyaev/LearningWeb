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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

@RequestMapping("/userPage")
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

    @RequestMapping("/{id}")
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

    @RequestMapping("/editProfile/{id}")
    public String editProfile(@PathVariable(value = "id") Long id, Model model) {
        Users user = userService.findUserById(id).orElseThrow();
        model.addAttribute("userPage", user);
        return "editProfile";
    }

    @PostMapping("/editProfile/{id}")
    public String saveEdit(@PathVariable(value = "id") Long id,
                           @RequestParam(name = "name", required = false) String name,
                           @RequestParam(name = "surname",required = false) String surname,
                           @RequestParam(name = "email", required = false) String email,
                           @RequestParam(name = "birthday", required = false)Date birthday,
                           @RequestParam(name = "photo", required = false)File photo) {
        Users user = userService.findUserById(id).orElseThrow();
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setBirthday(birthday);
        user.setPhoto(photo);
        userService.saveNewUser(user);
        return "redirect:/userPage/" + user.getId();
    }
}
