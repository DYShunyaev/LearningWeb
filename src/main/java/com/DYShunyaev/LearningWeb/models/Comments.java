package com.DYShunyaev.LearningWeb.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_id")
    private Long userId;

    @Column
    private String commentaryText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    @ToString.Exclude
    private Course withCourse;

    @Column(name = "course_id", insertable = false, updatable = false)
    private Long courseId;

    public Comments() {
    }

    public Comments(String userName, Long userId, String commentaryText) {
        this.userName = userName;
        this.commentaryText = commentaryText;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", commentaryText='" + commentaryText + '\'' +
                ", course=" + withCourse +
                ", courseId=" + courseId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comments comments = (Comments) o;
        return Objects.equals(id, comments.id) && Objects.equals(userName, comments.userName) && Objects.equals(commentaryText, comments.commentaryText) && Objects.equals(withCourse, comments.withCourse) && Objects.equals(courseId, comments.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, commentaryText, withCourse, courseId);
    }
}
