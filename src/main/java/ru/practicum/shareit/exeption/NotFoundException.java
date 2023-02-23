package ru.practicum.shareit.exeption;

public class NotFoundException extends NullPointerException {
    public NotFoundException(String message) {
        super(message);
    }
}
