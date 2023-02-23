package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    public static final String USER_ID_IN_HEADER = "X-Sharer-User-Id";

    private final ItemService itemService;

    @PostMapping
    public ItemDto createItem(@RequestHeader(USER_ID_IN_HEADER) int idOwner, @Valid @RequestBody ItemDto itemDto) {
        log.info("method = 'POST' endpoint = '/users'");
        Item item = ItemMapper.toItem(itemDto);
        return ItemMapper.toItemDto(itemService.createItem(idOwner, item));
    }

    @PatchMapping("/{idItem}")
    public ItemDto updateItem(
            @RequestHeader(USER_ID_IN_HEADER) int idOwner, @PathVariable int idItem, @RequestBody ItemDto itemDto
    ) {
        log.info("method = 'PATCH' endpoint = '/users/{}'", idItem);
        Item item = ItemMapper.toItem(itemDto);
        return ItemMapper.toItemDto(itemService.updateItem(idOwner, idItem, item));
    }

    @GetMapping("/{idItem}")
    public ItemDto getItemById(@RequestHeader(USER_ID_IN_HEADER) int idOwner, @PathVariable int idItem) {
        log.info("method = 'GET' endpoint = '/users/{}'", idItem);
        return ItemMapper.toItemDto(itemService.getItemById(idOwner, idItem));
    }

    @GetMapping
    public List<ItemDto> getAllItems(@RequestHeader(USER_ID_IN_HEADER) int idOwner) {
        log.info("method = 'GET' endpoint = '/users'");
        return ItemMapper.toItemDtoList(itemService.getAllItems(idOwner));
    }

    @GetMapping("/search")
    public List<ItemDto> findItemByParams(@RequestParam String text) {
        if (text.isBlank()) {
            return new ArrayList<>();
        }
        log.info("method = 'GET' endpoint = '/users/search{}'", text);
        return ItemMapper.toItemDtoList(itemService.getItemsByText(text));
    }
}
