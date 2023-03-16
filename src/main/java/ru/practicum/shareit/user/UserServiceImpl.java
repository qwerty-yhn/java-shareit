package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.error.NotFoundException;
import ru.practicum.shareit.user.exeption.UserNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Transactional
    @Override
    public User createUser(User user) {
        return repository.save(user);
    }

    @Transactional
    @Override
    public User updateUser(Long id, User user) {

        user.setId(id);
        User userTemp = updateParameters(user);
        User userResult = getUser(id);

        userResult.setId(userTemp.getId());
        userResult.setName(userTemp.getName());
        userResult.setEmail(userTemp.getEmail());

        return userResult;
    }

    @Transactional
    @Override
    public User getUser(Long id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    private User updateParameters(User user) {

        for (int i = 0; i < repository.findAll().size(); i++) {
            if (repository.findAll().get(i).getId().equals(user.getId())) {
                if (user.getName() == null) {
                    user.setName(repository.findAll().get(i).getName());
                }
                if (user.getEmail() == null) {
                    user.setEmail(repository.findAll().get(i).getEmail());
                }
            }
        }
        return user;
    }
}
