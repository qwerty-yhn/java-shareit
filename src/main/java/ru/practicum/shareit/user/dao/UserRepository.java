package ru.practicum.shareit.user.dao;

import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Map;

public interface UserRepository {
    User createUser(User user);

    List<User> getUsers();

    User updateUser(int id, User user);

    Map<Integer, User> getUsersMap();

    void deleteUser(int id);

    Boolean existence(int id);
}
