package com.DYShunyaev.LearningWeb.repositories;

import com.DYShunyaev.LearningWeb.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
//    boolean existByUsername(User use);
    Optional<User> findByUserName(String username);
}
