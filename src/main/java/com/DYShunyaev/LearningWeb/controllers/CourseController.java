package com.DYShunyaev.LearningWeb.controllers;

import com.DYShunyaev.LearningWeb.models.Comments;
import com.DYShunyaev.LearningWeb.models.Course;
import com.DYShunyaev.LearningWeb.models.Users;
import com.DYShunyaev.LearningWeb.services.CommentsService;
import com.DYShunyaev.LearningWeb.services.CourseService;
import com.DYShunyaev.LearningWeb.services.UserService;
import org.apache.commons.io.FileUtils;
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
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequestMapping("/course")
@Controller
public class CourseController {
    private final CourseService courseService;
    private final UserService userService;
    private final CommentsService commentsService;

    @Autowired
    public CourseController(CourseService courseService, UserService userService,
                            CommentsService commentsService) {
        this.courseService = courseService;
        this.userService = userService;
        this.commentsService = commentsService;
    }

    @Value("${upload.path}")
    private String uploadPath;

    @RequestMapping("/{id}")
    public String courseView(@PathVariable(value = "id", required = false) long courseId, Model model) {
        model.addAttribute("authUser", userService.getAuthorizationUser());

        if (!courseService.existCourseById(courseId)) {
            String message = "This user not founded.";
            model.addAttribute("error", message);
            return "error";
        }
        Course course = courseService.findCourseById(courseId).orElseThrow();
        model.addAttribute("coursePage", course);

        Users user = userService.findUserById(course.getTeacherId()).orElseThrow();
        model.addAttribute("author", user.getUserName());

        Set<Users> usersSet = course.getUsersSubs();

        model.addAttribute("usersList", usersSet);
        model.addAttribute("usersSize", usersSet.size());

        List<Comments> commentsList = commentsService.getCommentsByCourseId(courseId);
        model.addAttribute("comments", commentsList);

        Comments comment = new Comments();
        model.addAttribute("comment", comment);

        return "courses/coursePage";
    }
    @RequestMapping("/byUser/{id}/createCourse")
    public String createCourse(@PathVariable(name = "id") long userId,
                               Model model, Model userModel) {
        model.addAttribute("authUser", userService.getAuthorizationUser());
        Course course = new Course();
        model.addAttribute("course", course);
        return "courses/createCourse";
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
        course.setTeacher(user);
        String upload = uploadPath + "/courses/" + courseName;
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

    @RequestMapping("/editProfileCourse/{id}")
    public String editProfile(@PathVariable(value = "id") Long id, Model model) {
        Course course = courseService.findCourseById(id).orElseThrow();
        model.addAttribute("authUser", userService.getAuthorizationUser());
        model.addAttribute("coursePage", course);
        return "courses/editProfileCourse";
    }

    @PostMapping("/editProfileCourse/{id}")
    public String saveEdit(@PathVariable(value = "id") Long id,
                           @RequestParam(name = "courseName", required = false) String courseName,
                           @RequestParam(name = "coursePreview",required = false) String coursePreview,
                           @RequestParam(name = "courseContent", required = false) String courseContent,
                           @RequestParam(value = "video")MultipartFile file) throws IOException {
        courseName = getRegExCourseName(courseName);
        Course course = courseService.findCourseById(id).orElseThrow();
        course.setCourseName(courseName);
        course.setCoursePreview(coursePreview);
        course.setCourseContent(courseContent);

        String upload = uploadPath + "/courses/" + courseName + "/content/";
        if (file != null) {
            File uploadDir = new File(upload);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(upload + resultFileName));
            course.setContentFileName(resultFileName);
        }
        courseService.createNewCourse(course);
        return "redirect:/course/" + id;
    }

    @RequestMapping("/delete/{user_id}/{course_id}")
    public String deleteByUser(@PathVariable(name = "user_id") long userId,
                               @PathVariable(name = "course_id") long courseId) {

        courseService.deleteCourse(courseId);

        return "redirect:/userPage/" + userId;
    }

    @RequestMapping("/subscribe/{user_id}/{course_id}")
    public String subscribe(@PathVariable(name = "user_id") long userId,
                            @PathVariable(name = "course_id") long courseId) {
        Users user = userService.findUserById(userId).orElseThrow();
        Course course = courseService.findCourseById(courseId).orElseThrow();

        courseService.subscribe(user, course);

        return "redirect:/course/" + courseId;
    }

    @RequestMapping("/unsubscribe/{user_id}/{course_id}")
    public String unsubscribe(@PathVariable(name = "user_id") long userId,
                            @PathVariable(name = "course_id") long courseId) {
        Users user = userService.findUserById(userId).orElseThrow();
        Course course = courseService.findCourseById(courseId).orElseThrow();

        courseService.unsubscribe(user, course);

        return "redirect:/course/" + courseId;
    }
}
