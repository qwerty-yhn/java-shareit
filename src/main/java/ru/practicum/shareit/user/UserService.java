package ru.practicum.shareit.user;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User createUser(User user);

    User updateUser(Long id, User user);

    User getUser(Long id);

    void deleteUser(Long id);

    List<User> getAllUsers();
}