package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.Create;
import ru.practicum.shareit.Update;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserDto createUser(@Validated(Create.class) @RequestBody UserDto userDto) {
        log.info("method = 'POST' endpoint = '/users' function = 'create user'");
        User user = UserMapper.toUser(userDto);
        return UserMapper.toUserDto(userService.createUser(user));
    }

    @PatchMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @Validated(Update.class) @RequestBody UserDto userDto) {
        log.info("method = 'PATCH' endpoint = '/users/{} function = 'update user''", id);
        User user = UserMapper.toUser(userDto);
        return UserMapper.toUserDto(userService.updateUser(id, user));
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id) {
        log.info("method = 'GET' endpoint = '/users/{}' function = 'get user by identify'", id);
        return UserMapper.toUserDto(userService.getUser(id));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        log.info("method = 'DELETE' endpoint = '/users/{}' function = 'delete user'", id);
        userService.deleteUser(id);
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        log.info("method = 'GET' endpoint = '/users' function = 'get all users'");
        return UserMapper.toUserDtoList(userService.getAllUsers());
    }
}
