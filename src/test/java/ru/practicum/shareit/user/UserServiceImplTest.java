package ru.practicum.shareit.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    private final UserDto userDto = new UserDto(
            null,
            "test",
            "test@gmail.dom");

    private final User user = new User(
            1L,
            "test",
            "test@gmail.dom");

    @Test
    void createUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(userDto);

        Assertions.assertNotNull(createdUser);
        Assertions.assertEquals(1, createdUser.getId());
        Assertions.assertEquals(user.getName(), createdUser.getName());
        Assertions.assertEquals(user.getEmail(), createdUser.getEmail());

        verify(userRepository, times(1)).save(any(User.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void findAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> users = userService.getAllUsers();

        Assertions.assertNotNull(users);
        Assertions.assertEquals(1, users.size());
        Assertions.assertEquals(1, users.get(0).getId());
        Assertions.assertEquals(userDto.getName(), users.get(0).getName());
        Assertions.assertEquals(userDto.getEmail(), users.get(0).getEmail());

        verify(userRepository, times(1)).findAll();
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void findUserById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        User user = userService.getUser(1L);

        Assertions.assertNotNull(user);
        Assertions.assertEquals(1, user.getId());
        Assertions.assertEquals(userDto.getName(), user.getName());
        Assertions.assertEquals(userDto.getEmail(), user.getEmail());

        verify(userRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void updateUserById() {
        UserDto newUpdatedUserDto = new UserDto(1L, "test", "test@nail.com");
        User newUpdatedUser = new User(1L, "test", "test@nail.com");

        when(userRepository.save(any(User.class))).thenReturn(newUpdatedUser);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        User updatedUser = userService.updateUser(1L, newUpdatedUserDto);

        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals(1, updatedUser.getId());
        Assertions.assertEquals(newUpdatedUserDto.getName(), updatedUser.getName());
        Assertions.assertEquals(newUpdatedUserDto.getEmail(), updatedUser.getEmail());

        verify(userRepository, times(1)).save(any(User.class));
        verify(userRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void deleteUserById() {
        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(anyLong());
        verifyNoMoreInteractions(userRepository);
    }
}
