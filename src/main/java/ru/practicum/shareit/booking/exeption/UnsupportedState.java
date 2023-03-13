package ru.practicum.shareit.booking.exeption;

import ru.practicum.shareit.error.BadRequestException;

public class UnsupportedState extends BadRequestException {
    public UnsupportedState(String message) {
        super(String.format("Unknown state: %s", message));
    }
}
