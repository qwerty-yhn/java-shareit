package ru.practicum.shareit.item.dao;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository {
    Item createItem(Item item);

    Boolean existence(int item);

    Item updateItem(int idOwner, int idItem, Item item);

    Item getItem(int idItem);

    List<Item> getAllIItems(int idOwner);

    List<Item> getItemsByText(String toLowerCase);
}