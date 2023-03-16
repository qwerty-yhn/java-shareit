package ru.practicum.shareit.item.exeption;

import ru.practicum.shareit.error.NotFoundException;

public class ItemNotFoundByOwnerException extends NotFoundException {
    public ItemNotFoundByOwnerException(Long idOwner) {
        super("Item not found by Owner = " + idOwner);
    }
}
