package ru.practicum.shareit.booking.exeption;

import ru.practicum.shareit.booking.BookingState;
import ru.practicum.shareit.error.NotFoundException;

public class BookingNotFoundException extends NotFoundException {
    public BookingNotFoundException(Long bookingId) {
        super(String.format("Booking id = %s not found ", bookingId));
    }
}
