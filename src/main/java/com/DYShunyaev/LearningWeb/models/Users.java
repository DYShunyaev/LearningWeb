package com.DYShunyaev.LearningWeb.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@ToString
@AllArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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

    @Column
    private boolean active;

    @Column
    private String photoName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Course> courses = new ArrayList<>();

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

//    @ManyToMany(fetch = FetchType.LAZY,
//            cascade = {
//                CascadeType.PERSIST,
//                    CascadeType.MERGE
//            })
//    @JoinTable(
//            name = "courses_subscriptions",
//            joinColumns = {@JoinColumn(name = "course_id")},
//            inverseJoinColumns = { @JoinColumn(name = "user_id")}
//    )
//    private Set<Course> coursesSubs = new HashSet<>();

    public Users() {
    }

    public Users(String username, String name, String surname, char gender, String email, Date birthday, String password) {
        this.userName = username;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.email = email;
        this.birthday = birthday;
        this.password = password;
    }

    public Users(String username, String password) {
        this.userName = username;
        this.password = password;
    }

    public Users(String userName, String name, String surname, char gender, String email, Date birthday, String password, boolean active, String photoName, Set<Role> roles) {
        this.userName = userName;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.email = email;
        this.birthday = birthday;
        this.password = password;
        this.active = active;
        this.photoName = photoName;
        this.roles = roles;
    }
}
