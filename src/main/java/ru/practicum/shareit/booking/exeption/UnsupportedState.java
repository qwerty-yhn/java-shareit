package ru.practicum.shareit.booking.exeption;

import ru.practicum.shareit.booking.BookingState;
import ru.practicum.shareit.error.BadRequestException;

public class UnsupportedState extends BadRequestException {
    public UnsupportedState(BookingState state) {
        super(String.format("Unknown state: %s", state));
    }
}
