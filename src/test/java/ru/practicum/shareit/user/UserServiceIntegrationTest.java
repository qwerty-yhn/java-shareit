package ru.practicum.shareit.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.user.exeption.*;
import ru.practicum.shareit.user.UserDto;
import ru.practicum.shareit.user.UserService;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    private final UserDto userDto = new UserDto(
            null,
            "test",
            "test@gmail.dom");
    private final User user = new User(
            null,
            "test",
            "test@gmail.dom");

    @Test
    void createNewUser() {
        User createdUser = userService.createUser(userDto);

        Assertions.assertEquals(1L, createdUser.getId());
        Assertions.assertEquals(userDto.getName(), createdUser.getName());
        Assertions.assertEquals(userDto.getEmail(), createdUser.getEmail());
    }

    @Test
    void getUserByWrongId() {
        Long userId = 2L;

        Assertions
                .assertThrows(UserNotFoundException.class, () -> userService.getUser(userId));
    }
}