package ru.practicum.shareit.request.exeption;

import ru.practicum.shareit.error.BadRequestException;

public class NotExistDescriptionExeption extends BadRequestException {

    public NotExistDescriptionExeption() {
        super("Not exist param description");
    }
}
