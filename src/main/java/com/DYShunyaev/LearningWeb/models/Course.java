package com.DYShunyaev.LearningWeb.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

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

    @Column(name = "course_content")
    private String courseContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    public Course() {
    }

    public Course(String courseName, String coursePreview, String courseContent, Users user) {
        this.courseName = courseName;
        this.coursePreview = coursePreview;
        this.courseContent = courseContent;
        this.user = user;
    }
}
