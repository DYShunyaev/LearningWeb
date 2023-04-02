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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

@RequestMapping("/userPage")
@Controller
public class UserController {

    @Value("${upload.path}")
    private String uploadPath;
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
                           Model courseModel, Model authorizationUser ) {
        if (!userService.existUserById(id)) {
            String message = "This user not founded.";
            model.addAttribute("error", message);
            return "error";
        }
        authorizationUser.addAttribute("authUser", userService.getAuthorizationUser());
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
                           @RequestParam("photo")MultipartFile file) throws IOException {
        Users user = userService.findUserById(id).orElseThrow();
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setBirthday(birthday);
        String upload = uploadPath + "/" + user.getUserName();
        if (file != null) {
            File uploadDir = new File(upload);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(upload + "/" + resultFileName));
            user.setPhotoName(resultFileName);
        }
        userService.saveNewUser(user);
        return "redirect:/userPage/" + user.getId();
    }

    @RequestMapping("/editProfile/deletePhoto/{id}")
    public String removePhoto(@PathVariable(value = "id") Long id) {
        Users user = userService.findUserById(id).orElseThrow();
        String upload = uploadPath + "/" + user.getUserName();
        String fileName = user.getPhotoName();
        File file = new File(upload + "/" + fileName);
        boolean delete = file.delete();
        user.setPhotoName(null);
        userService.saveNewUser(user);
        return "redirect:/userPage/editProfile/" + user.getId();
    }
}
