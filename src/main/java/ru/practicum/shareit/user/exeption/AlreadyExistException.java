package ru.practicum.shareit.user.exeption;

import ru.practicum.shareit.error.ConflictException;

public class AlreadyExistException extends ConflictException {
    public AlreadyExistException(String message) {
        super(message);
    }
}
