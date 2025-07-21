package com.DYShunyaev.LearningWeb;

import com.DYShunyaev.LearningWeb.models.Users;
import com.DYShunyaev.LearningWeb.repositories.UserRepository;
import com.DYShunyaev.LearningWeb.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.sql.Date;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    private final Date birthday = new Date(2000, 1, 1);
    private final Users user = new Users(150L,"TestUser", "Dmitry", "Dmitryev", 'M',
            "usertest@gmail.com", birthday, "password");
    private Long id;
    @Test
    public void saveNewUserTest() {

        Mockito.when(userRepository.findByUserName("TestUser")).thenReturn(Optional.of(user));

        Users testUser = userService.findUserByUsernameFromTest("TestUser");
        id = testUser.getId();
        Assertions.assertEquals(user, testUser);
    }

    @Test
    public void findUserByIdTest() {
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));

        Users testUser = userService.findUserById(id).orElseThrow();
        Assertions.assertEquals(user, testUser);
    }

    @Test
    public void existUserByIdTrueTest() {
        boolean exist = userService.existUserById(id);
        Assertions.assertTrue(exist);
    }

//    @Test
//    public void existUserByIdFalseTest() {
//        boolean exist = userService.existUserById(100340L);
//        Assertions.assertFalse(exist);
//    }
}
