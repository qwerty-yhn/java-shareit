package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto) {
        log.info("method = 'POST' endpoint = '/users' function = 'create user'");
        return UserMapper.toUserDto(userService.createUser(userDto));
    }

    @PatchMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        log.info("method = 'PATCH' endpoint = '/users/{} function = 'update user''", id);
        return UserMapper.toUserDto(userService.updateUser(id, userDto));
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
