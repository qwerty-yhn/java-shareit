package ru.practicum.shareit.user.exeption;

import ru.practicum.shareit.error.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(Long id) {
        super("User not found " + id);
    }
}
