package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {

    Item createItem(int idOwner, Item item);

    Item updateItem(int idOwner, int idItem, Item item);

    Item getItemById(int idOwner, int idItem);

    List<Item> getAllItems(int idOwner);

    List<Item> getItemsByText(String text);
}
