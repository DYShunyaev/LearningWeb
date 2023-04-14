package com.DYShunyaev.LearningWeb.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@ToString
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
    @JoinColumn(name = "teacher_id")
    private Users user;

    @Column(name = "teacher_id", insertable = false, updatable = false)
    private Long teacherId;

//    @ManyToMany(fetch = FetchType.LAZY,
//            cascade = {
//                    CascadeType.PERSIST,
//                    CascadeType.MERGE
//            })
//    @JoinTable(
//            name = "users_subscriptions",
//            joinColumns = {@JoinColumn(name = "user_id")},
//            inverseJoinColumns = { @JoinColumn(name = "course_id")}
//    )
//    private Set<Users> usersSubs = new HashSet<>();
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
