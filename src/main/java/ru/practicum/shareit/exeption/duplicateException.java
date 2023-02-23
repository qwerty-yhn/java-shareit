package ru.practicum.shareit.exeption;

public class duplicateException extends RuntimeException {
    public duplicateException(String message) {
        super(message);
    }
}
