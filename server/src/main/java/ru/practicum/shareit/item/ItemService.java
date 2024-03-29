package ru.practicum.shareit.item;

import java.util.List;

public interface ItemService {
    Item createItem(Long idOwner, Item item, Long requesterId);

    Item updateItem(Long idOwner, Long idItem, Item item);

    ItemDto getItemById(Long idOwner, Long idItem);

    Item getItem(Long idItem);

    List<Item> getItemsByText(String text);

    CommentDto createComment(Long itemId, Long userId, CommentDto commentDto);

    ItemDto itemSetBooking(ItemDto itemDto);

    ItemDto itemSetComment(ItemDto itemDto);
}
