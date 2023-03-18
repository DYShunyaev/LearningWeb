package com.DYShunyaev.LearningWeb.services;

import com.DYShunyaev.LearningWeb.models.Users;
import com.DYShunyaev.LearningWeb.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private Users authorizationUser;
    private final UserRepository userRepository;

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
        return userRepository.existsById(id);
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
        authorizationUser = users;
        return User.withDefaultPasswordEncoder()
                .username(users.getUserName())
                .password(users.getPassword())
                .roles("USER")
                .build();
    }


}
