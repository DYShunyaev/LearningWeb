package com.DYShunyaev.LearningWeb.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Course> courses = new ArrayList<>();

    @Column
    private String userName;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private char gender;

    @Column
    private String email;

    @Column
    private Date birthday;

    @Column
    private String password;

    public User() {
    }

    public User(String username, String name, String surname, char gender, String email, Date birthday, String password) {
        this.userName = username;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.email = email;
        this.birthday = birthday;
        this.password = password;
    }
}
