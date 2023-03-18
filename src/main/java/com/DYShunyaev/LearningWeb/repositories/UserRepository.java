package com.DYShunyaev.LearningWeb.repositories;

import com.DYShunyaev.LearningWeb.models.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<Users, Long> {
//    boolean existByUsername(Users use);
    Optional<Users> findByUserName(String username);
    Optional<Users> findByName(String name);
}
