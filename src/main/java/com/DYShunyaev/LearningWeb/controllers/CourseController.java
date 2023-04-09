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
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequestMapping("/course")
@Controller
public class CourseController {
    private final CourseService courseService;
    private final UserService userService;

    @Autowired
    public CourseController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @Value("${upload.path}")
    private String uploadPath;

    @RequestMapping("/byUser/{id}/createCourse")
    public String createCourse(@PathVariable(name = "id") long userId,
                               Model model, Model userModel) {
        Users user = userService.findUserById(userId).orElseThrow();
        userModel.addAttribute("userPage", user);
        Course course = new Course();
        model.addAttribute("course", course);
        return "createCourse";
    }

    @PostMapping("/byUser/{id}/createCourse")
    public String saveCourse(@PathVariable(name = "id") long user_id,
                             @RequestParam(name = "courseName") String courseName,
                             @RequestParam(name = "coursePreview") String coursePreview,
                             @RequestParam(name = "courseContent") String courseContent,
                             @RequestParam("photo")MultipartFile file) throws Exception {
        courseName = getRegExCourseName(courseName);
        Course course = new Course(courseName, coursePreview, courseContent);
        Users user = userService.findUserById(user_id).orElseThrow();
        course.setUser(user);
        String upload = uploadPath + "/" + user.getUserName() + "/" + courseName;
        if (file != null) {
            File uploadDir = new File(upload);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(upload + "/" + resultFileName));
            course.setPreviewName(resultFileName);
        }
        courseService.createNewCourse(course);
        return "redirect:/userPage/" + user.getId();
    }

    private static String getRegExCourseName(String courseName) {
        Pattern pattern = Pattern.compile("(\\w.+\\b)");
        Matcher matcher = pattern.matcher(courseName);
        StringBuilder builder = new StringBuilder();
        while (matcher.find()){
            for (int i = matcher.start(); i < matcher.end(); i++) {
                builder.append(courseName.charAt(i));
            }
        }
        return builder.toString();
    }

    @RequestMapping("/delete/{user_id}/{course_id}")
    public String deleteByUser(@PathVariable(name = "user_id") long user_id,
                               @PathVariable(name = "course_id") long course_id) {
        courseService.deleteCourse(course_id);
        return "redirect:/userPage/" + user_id;
    }
}
