package com.DYShunyaev.LearningWeb.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
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
    @ToString.Exclude
    private Users teacher;

    @Column(name = "teacher_id", insertable = false, updatable = false)
    private Long teacherId;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "users_subscriptions",
            joinColumns = {@JoinColumn(name = "course_id")},
            inverseJoinColumns = { @JoinColumn(name = "users_id")}
    )
    @ToString.Exclude
    private Set<Users> usersSubs = new HashSet<>();

    public Course() {
    }

    public Course(String courseName, String coursePreview, String courseContent) {
        this.courseName = courseName;
        this.coursePreview = coursePreview;
        this.courseContent = courseContent;
    }

    public Course(String courseName, String coursePreview, String previewName, String contentFileName,
                  String courseContent, Users teacher) {
        this.courseName = courseName;
        this.coursePreview = coursePreview;
        this.previewName = previewName;
        this.contentFileName = contentFileName;
        this.courseContent = courseContent;
        this.teacher = teacher;
    }

    public Set<Users> getUsersSubs() {
        return usersSubs;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseName='" + courseName + '\'' +
                ", coursePreview='" + coursePreview + '\'' +
                ", previewName='" + previewName + '\'' +
                ", contentFileName='" + contentFileName + '\'' +
                ", courseContent='" + courseContent + '\'' +
                ", teacher=" + teacher +
                ", teacherId=" + teacherId +
                ", usersSubs=" + usersSubs +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Course course = (Course) o;
        return id != null && Objects.equals(id, course.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
