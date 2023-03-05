package ru.practicum.shareit.exeption;

public class ParameterNotSetException extends NullPointerException {
    public ParameterNotSetException(String message) {
        super(message);
    }
}
