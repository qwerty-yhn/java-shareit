package ru.practicum.shareit.booking.exeption;

import ru.practicum.shareit.error.NotFoundException;

public class BookingOfItemNotFoundException extends NotFoundException {

    public BookingOfItemNotFoundException(Long id) {
        super(String.format("item not found id = %s", id));
    }
}
