package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);

    User updateUser(int id, User user);

    User getUser(int id);

    void deleteUser(int id);

    List<User> getAllUsers();

}
