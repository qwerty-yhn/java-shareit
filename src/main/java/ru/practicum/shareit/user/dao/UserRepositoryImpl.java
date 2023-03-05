package ru.practicum.shareit.user.dao;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserRepositoryImpl implements UserRepository {
    private final Map<Integer, User> users = new HashMap<>();
    private int inc = 0;

    @Override
    public User createUser(User user) {
        user.setId(genId());
        users.put(user.getId(), user);
        return user;
    }

    private int genId() {
        return ++inc;
    }

    public List<User> getUsers() {
        final List<User> userToList = new ArrayList<>();
        for (Integer i : users.keySet()) {
            userToList.add(users.get(i));
        }
        return userToList;
    }

    @Override
    public User updateUser(int id, User user) {
        users.remove(user.getId());
        users.put(id, user);
        return user;
    }

    public Map<Integer, User> getUsersMap() {
        return users;
    }

    @Override
    public void deleteUser(int id) {
        users.remove(id);
    }

    @Override
    public Boolean existence(int id) {
        if (!users.containsKey(id)) {
            return true;
        } else {
            return false;
        }
    }
}
