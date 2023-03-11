package com.DYShunyaev.LearningWeb.services;

import com.DYShunyaev.LearningWeb.models.User;
import com.DYShunyaev.LearningWeb.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveNewUser(User user) {
        userRepository.save(user);
    }

    public Optional<User> showById(Long id) {
        return userRepository.findById(id);
    }

    public boolean existById(Long id) {
        return userRepository.existsById(id);
    }

//    public Optional<User> showByUserName(String userName) {
//        return ;
//    }

    public boolean existByUserName(String userName) {
        try{
            Optional<User> user = userRepository.findByUserName(userName);
            return user.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

//    public boolean existByUserName(User user) {
//        return userRepository.existByUsername(user);
//    }
}
