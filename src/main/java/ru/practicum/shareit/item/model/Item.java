package ru.practicum.shareit.item.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.util.Objects;

@Data
@AllArgsConstructor
@Builder
public class Item {
    private int id;
    private String name;
    private String description;
    private Boolean available;
    private User owner;
}
