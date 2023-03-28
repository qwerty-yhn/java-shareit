package ru.practicum.shareit.booking.exeption;

import ru.practicum.shareit.error.NotFoundException;

public class BookingHaveOwnerNotFoundException extends NotFoundException {
    public BookingHaveOwnerNotFoundException(Long owner, Long booking) {
        super("Owner = " + owner + " not have booking = " + booking);
    }
}
