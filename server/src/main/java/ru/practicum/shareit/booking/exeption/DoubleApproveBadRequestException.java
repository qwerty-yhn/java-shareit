package ru.practicum.shareit.booking.exeption;

import ru.practicum.shareit.error.BadRequestException;

public class DoubleApproveBadRequestException extends BadRequestException {
    public DoubleApproveBadRequestException(Long booking) {
        super("dual status booking " + booking);
    }
}
