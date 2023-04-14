package com.DYShunyaev.LearningWeb.controllers;

import com.DYShunyaev.LearningWeb.models.Role;
import com.DYShunyaev.LearningWeb.models.Users;
import com.DYShunyaev.LearningWeb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.Collections;

@Controller
public class AuthorizationController {
    UserController userController = new UserController();
    private final UserService userService;

    @Autowired
    public AuthorizationController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/registration")
    public String registration(Model model) {
        Users user = new Users();
        model.addAttribute("user", user);
        return "registration";
    }

    @PostMapping("/registration")
    public String saveNewUser(@RequestParam(name = "userName", required = false) String username,
                              @RequestParam(name = "name", required = false) String name,
                              @RequestParam(name = "surname", required = false) String surname,
                              @RequestParam(name = "gender", required = false) char gender,
                              @RequestParam(name = "email", required = false) String email,
                              @RequestParam(name = "birthday", required = false) Date birthday,
                              @RequestParam(name = "password", required = false) String password,
                              Model model) {
        if (!userService.existByUserName(username)) {
            String message = "Users with this username is already registered!";
            model.addAttribute("error", message);
            Users user = new Users();
            model.addAttribute("user", user);
            return registration(model);
        }
        Users user = new Users(username, name, surname, gender, email, birthday, password);
        if (username.equals("admin")) {
            user.setRoles(Collections.singleton(Role.ADMIN));
        }
        else user.setRoles(Collections.singleton(Role.USER));
        userService.saveNewUser(user);
        return "redirect: /login";
    }
}
