package ru.practicum.shareit.item.exeption;

import ru.practicum.shareit.error.NotFoundException;

public class ItemNotFoundException extends NotFoundException {
    public ItemNotFoundException(Long item) {
        super("Not found item " + item);
    }
}
