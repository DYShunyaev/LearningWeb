package com.DYShunyaev.LearningWeb.repositories;

import com.DYShunyaev.LearningWeb.models.Course;
import com.DYShunyaev.LearningWeb.models.Role;
import com.DYShunyaev.LearningWeb.models.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends CrudRepository<Users, Long> {
//    boolean existByUsername(Users use);
    Optional<Users> findByUserName(String username);
    Optional<Users> findByName(String name);

//    Set<Users> findUsersByCourseId(Long courseId);

}
