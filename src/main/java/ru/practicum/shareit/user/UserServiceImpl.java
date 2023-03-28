package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.user.exeption.UserNotFoundException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public
class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Transactional
    @Override
    public User createUser(UserDto userDto) {
        User user = UserMapper.toUser(userDto);
        return repository.save(user);
    }

    @Transactional
    @Override
    public User updateUser(Long id, UserDto userDto) {

        User userFromMap = getUser(id);
        User userFromDto = UserMapper.toUser(userDto);

        userFromMap.setName(Objects.requireNonNullElse(userFromDto.getName(), userFromMap.getName()));
        userFromMap.setEmail(Objects.requireNonNullElse(userFromDto.getEmail(), userFromMap.getEmail()));

        return repository.save(userFromMap);

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

}
