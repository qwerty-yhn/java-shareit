package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dao.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dao.UserRepository;
import ru.practicum.shareit.exeption.UserNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public Item createItem(int idOwner, Item item) {
        if (userRepository.existence(idOwner)) {
            throw new UserNotFoundException("object not exist");
        }
        item.setOwner(userRepository.getUsersMap().get(idOwner));
        return itemRepository.createItem(item);
    }

    @Override
    public Item updateItem(int idOwner, int idItem, Item item) {
        if (userRepository.existence(idOwner)) {
            throw new UserNotFoundException("object not exist");
        }
        Item itemPrev = itemRepository.getItem(idItem);
        if (itemPrev.getOwner().getId() != idOwner) {
            throw new UserNotFoundException(" not valid Owner of object");
        }
        if (item.getName() == null) {
            item.setName(itemPrev.getName());
        }
        if (item.getDescription() == null) {
            item.setDescription(itemPrev.getDescription());
        }
        if (item.getAvailable() == null) {
            item.setAvailable(itemPrev.getAvailable());
        }
        item.setOwner(userRepository.getUsersMap().get(idOwner));
        item.setId(idItem);
        return itemRepository.updateItem(idOwner, idItem, item);
    }

    @Override
    public Item getItemById(int idOwner, int idItem) {
        Item item = itemRepository.getItem(idItem);
        return item;
    }

    @Override
    public List<Item> getAllItems(int idOwner) {
        return itemRepository.getAllIItems(idOwner);
    }

    @Override
    public List<Item> getItemsByText(String text) {
        return itemRepository.getItemsByText(text);
    }
}
