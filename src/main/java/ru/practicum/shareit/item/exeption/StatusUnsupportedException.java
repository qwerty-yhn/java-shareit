package ru.practicum.shareit.item.exeption;

import ru.practicum.shareit.error.BadRequestException;

public class StatusUnsupportedException extends BadRequestException {
    public StatusUnsupportedException(Long userId, Long itemId) {
        super("User " + userId + " can't comment item " + itemId);
    }
}
