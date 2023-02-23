package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.dao.UserRepository;
import ru.practicum.shareit.exeption.UserAlreadyExistsByEmailException;
import ru.practicum.shareit.exeption.UserNotFoundException;
import ru.practicum.shareit.exeption.ParameterNotSetException;
import ru.practicum.shareit.exeption.AlreadyExistException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public User createUser(User user) {
        checkAssert(user);
        return repository.createUser(user);
    }

    @Override
    public User updateUser(int id, User user) {
        user.setId(id);
        checkExist(user);
        return repository.updateUser(id, updateParameters(user));
    }

    @Override
    public User getUser(int id) {
        return repository.getUsersMap().get(id);
    }

    @Override
    public void deleteUser(int id) {
        repository.deleteUser(id);
    }

    @Override
    public List<User> getAllUsers() {
        return repository.getUsers();
    }

    private User updateParameters(User user) {

        for (int i = 0; i < repository.getUsers().size(); i++) {
            if (repository.getUsers().get(i).getId() == user.getId()) {
                if (user.getName() == null) {
                    user.setName(repository.getUsers().get(i).getName());
                }
                if (user.getEmail() == null) {
                    user.setEmail(repository.getUsers().get(i).getEmail());
                }
            }
        }
        return user;
    }

    private void checkAssert(User user) {

        if (user.getName() == null || user.getEmail() == null) {
            throw new ParameterNotSetException("Object's parameter not set");
        }

        for (int i = 0; i < repository.getUsers().size(); i++) {
            if (repository.getUsers().get(i).getEmail().equals(user.getEmail())) {
                throw new UserAlreadyExistsByEmailException("duplicate email");
            }
        }
    }

    private void checkExist(User user) {

        if (repository.existence(user.getId())) {
            throw new UserNotFoundException("object not exist");
        }
        for (Integer i : repository.getUsersMap().keySet()) {
            if (repository.getUsersMap().get(i).getEmail().equals(user.getEmail()) && user.getId() != i) {
                throw new AlreadyExistException("email already exist");
            }
        }
    }
}
