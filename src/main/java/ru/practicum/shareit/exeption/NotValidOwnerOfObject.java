package ru.practicum.shareit.exeption;

public class NotValidOwnerOfObject extends NullPointerException {
    public NotValidOwnerOfObject(String message) {
        super(message);
    }
}