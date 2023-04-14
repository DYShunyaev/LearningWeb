package com.DYShunyaev.LearningWeb.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.sql.Date;
import java.util.*;

@Entity
@Getter
@Setter
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

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Course> courses = new ArrayList<>();

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "users_subscriptions",
            joinColumns = {@JoinColumn(name = "users_id")},
            inverseJoinColumns = { @JoinColumn(name = "course_id")}
    )
    @ToString.Exclude
    private Set<Course> coursesSubs = new HashSet<>();

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

    public Set<Course> getCoursesSubs() {
        return coursesSubs;
    }

    public void setCoursesSubs(Set<Course> coursesSubs) {
        this.coursesSubs = coursesSubs;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", gender=" + gender +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                ", password='" + password + '\'' +
                ", active=" + active +
                ", photoName='" + photoName + '\'' +
                ", courses=" + courses +
                ", roles=" + roles +
                ", coursesSubs=" + coursesSubs +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Users users = (Users) o;
        return id != null && Objects.equals(id, users.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
