package ru.practicum.shareit.user;

import java.util.List;

public interface UserService {

    User createUser(UserDto userDto);

    User updateUser(Long id, UserDto userDto);

    User getUser(Long id);

    void deleteUser(Long id);

    List<User> getAllUsers();
}