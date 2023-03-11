package com.DYShunyaev.LearningWeb.controllers;

import com.DYShunyaev.LearningWeb.models.Course;
import com.DYShunyaev.LearningWeb.models.User;
import com.DYShunyaev.LearningWeb.services.CourseService;
import com.DYShunyaev.LearningWeb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.List;

@Controller
public class MainController {

    private final UserService userService;
    private final CourseService courseService;

    @Autowired
    public MainController(UserService userService, CourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    @RequestMapping("/")
    public String mainView() {
        return "mainView";
    }

    @RequestMapping("/registration")
    public String registration(Model model, Model model2) {
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }

    @PostMapping("/registration")
    public String saveNewUser(@RequestParam(name = "userName", required = false) String username,
                              @RequestParam(name = "name", required = false) String name,
                              @RequestParam(name = "surname", required = false) String surname,
                              @RequestParam(name = "gender", required = false) char gender,
                              @RequestParam(name = "email", required = false) String email,
                              @RequestParam(name = "birthday", required = false)Date birthday,
                              @RequestParam(name = "password", required = false) String password,
                              Model model, Model model1) {
        if (!userService.existByUserName(username)) {
            String message = "User with this username is already registered!";
            model1.addAttribute("error", message);
            User user = new User();
            model.addAttribute("user", user);
            return registration(model,model1);
        }
        User user = new User(username, name, surname, gender, email, birthday, password);
        userService.saveNewUser(user);
        Long id = user.getId();
        return userPage(id, model, model1);
    }

    @RequestMapping("/userPage/{id}")
    public String userPage(@PathVariable(value = "id", required = false) long id, Model model,
                           Model courseModel) {
        if (!userService.existById(id)) {
            String message = "This user not founded.";
            model.addAttribute("error", message);
            return "error";
        }
        User user = userService.showById(id).orElseThrow();
        model.addAttribute("userPage", user);

        List<Course> courseList = courseService.showCoursesByUserId(user.getId());
        courseModel.addAttribute("courses", courseList);
        return "userPage";
    }
}
