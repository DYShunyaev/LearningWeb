package com.DYShunyaev.LearningWeb.models;

import java.util.List;

public enum Role {
    USER,
    TEACHER,
    ADMIN;

    public static List<Role> getRole() {
        List<Role> roles = List.of(USER,TEACHER,ADMIN);
        return roles;
    }
}
