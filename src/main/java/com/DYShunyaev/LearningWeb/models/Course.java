package com.DYShunyaev.LearningWeb.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "course_preview")
    private String coursePreview;

    @Column(name = "course_preview_name")
    private String previewName;

    @Column(name = "course_content_name")
    private String contentFileName;

    @Column(name = "course_content")
    private String courseContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Users> usersList = new ArrayList<>();
    public Course() {
    }

    public Course(String courseName, String coursePreview, String courseContent) {
        this.courseName = courseName;
        this.coursePreview = coursePreview;
        this.courseContent = courseContent;
    }

    public Course(String courseName, String coursePreview, String previewName, String contentFileName,
                  String courseContent, Users user) {
        this.courseName = courseName;
        this.coursePreview = coursePreview;
        this.previewName = previewName;
        this.contentFileName = contentFileName;
        this.courseContent = courseContent;
        this.user = user;
    }
}
