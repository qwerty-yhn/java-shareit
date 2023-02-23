package ru.practicum.shareit.item.dao;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;
import java.util.*;

@Repository
public class ItemRepositoryImpl implements ItemRepository {
    private final Map<Integer, Item> items = new HashMap<>();
    private int inc = 0;

    private int genId() {
        return ++inc;
    }

    @Override
    public Item createItem(Item item) {
        item.setId(genId());
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public Boolean existence(int item) {
        if (!items.containsKey(item)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Item updateItem(int idOwner, int idItem, Item item) {

        items.put(item.getId(), item);
        return item;
    }

    @Override
    public Item getItem(int idItem) {
        return items.get(idItem);
    }

    @Override
    public List<Item> getAllIItems(int idOwner) {
        final List<Item> itemsToList = new ArrayList<>();
        for (Integer i : items.keySet()) {
            if (items.get(i).getOwner().getId() == idOwner) {
                itemsToList.add(items.get(i));
            }
        }
        return itemsToList;
    }

    @Override
    public List<Item> getItemsByText(String text) {

        final List<Item> itemsToList = new ArrayList<>();
        for (Integer i : items.keySet()) {
            if (items.get(i).getAvailable()) {
                if (items.get(i).
                        getName().
                        toLowerCase().
                        contains(text.toLowerCase()) || items.get(i).
                        getDescription().
                        toLowerCase().
                        contains(text.toLowerCase())) {
                    itemsToList.add(items.get(i));
                }
            }
        }
        return itemsToList;
    }
}