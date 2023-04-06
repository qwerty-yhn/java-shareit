package ru.practicum.shareit.booking.exeption;

import ru.practicum.shareit.error.BadRequestException;

public class EndEqualAndBeforeStartException extends BadRequestException {
    public EndEqualAndBeforeStartException() {
        super("Invalid start or end time");
    }
}
