package ru.practicum.shareit.booking.exeption;

import ru.practicum.shareit.error.BadRequestException;
import ru.practicum.shareit.item.Item;

public class BookingUnavailableBadRequestException extends BadRequestException {
    public BookingUnavailableBadRequestException(Item item) {
        super("Biking state is unavaible item = " + item.getId());
    }
}
