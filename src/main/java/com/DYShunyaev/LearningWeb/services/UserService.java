package com.DYShunyaev.LearningWeb.services;

import com.DYShunyaev.LearningWeb.models.Role;
import com.DYShunyaev.LearningWeb.models.Users;
import com.DYShunyaev.LearningWeb.repositories.UserRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class UserService {

    private Users authorizationUser;
    private final UserRepository userRepository;
    private UserDetails ExceptionMappingAuthenticationFailureHandler;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveNewUser(Users user) {
        userRepository.save(user);
    }

    public Optional<Users> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<Users> findAllUsers() {
        return (List<Users>) userRepository.findAll();
    }

    public boolean existUserById(Long id) {
        return !userRepository.existsById(id);
    }


    public boolean existByUserName(String userName) {
        try{
            Optional<Users> user = userRepository.findByUserName(userName);
            return user.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public Users getAuthorizationUser() {
        return authorizationUser;
    }

    public UserDetails findUserByUsername(String username) {
        Users users = userRepository.findByUserName(username).orElseThrow();
//        if (!users.isActive() && !users.getUserName().equals("admin")) {
//            return ExceptionMappingAuthenticationFailureHandler;
//        }
        authorizationUser = users;
        return User.withDefaultPasswordEncoder()
                .username(users.getUserName())
                .password(users.getPassword())
                .roles(checkRole(users.getRoles()))
                .build();
    }

    public Users findUserByUsernameFromTest(String username) {
        return userRepository.findByUserName(username).orElseThrow();
    }

    private static String checkRole(Set<Role> roles) {
        if (roles.stream().anyMatch(role -> role == Role.ADMIN)) return "ADMIN";
        else if (roles.stream().anyMatch(role -> role == Role.TEACHER)) return "TEACHER";
        return "USER";
    }

    public void deleteUserById(Long id) {
        Users user = userRepository.findById(id).orElseThrow();
        File file = new File("usersPhoto/" + user.getUserName());
        try {
            FileUtils.deleteDirectory(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        userRepository.deleteById(id);
    }

//    public void deleteUserByUsername(String username) {
//        Users user = userRepository.findByUserName(username).orElseThrow();
//        File file = new File("usersPhoto/" + user.getUserName());
//        try {
//            FileUtils.deleteDirectory(file);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        userRepository.deleteUserByUsername(username);
//    }


}
