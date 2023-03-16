package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.shareit.constant.Constants.USER_ID;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemRepository itemRepository;
    private final ItemService itemService;

    @PostMapping
    public ItemDto createItem(@RequestHeader(USER_ID) Long idOwner,
                              @Valid @RequestBody ItemDto itemDto) {
        log.info("method = 'POST' endpoint = '/users' function = 'create item'");
        Item item = ItemMapper.toItem(itemDto);
        return ItemMapper.toItemDto(itemService.createItem(idOwner, item));
    }

    @PatchMapping("/{idItem}")
    public ItemDto updateItem(@RequestHeader(USER_ID) Long idOwner,
                              @PathVariable Long idItem,
                              @RequestBody ItemDto itemDto
    ) {
        log.info("method = 'PATCH' endpoint = '/users/{} function = 'update item''", idItem);
        Item item = ItemMapper.toItem(itemDto);
        return ItemMapper.toItemDto(itemService.updateItem(idOwner, idItem, item));
    }

    @GetMapping("/{idItem}")
    public ItemDto getItemById(@RequestHeader(USER_ID) Long idOwner,
                               @PathVariable Long idItem) {
        log.info("method = 'GET' endpoint = '/users/{}' function = 'get item by identify'", idItem);
        return itemService.getItemById(idOwner, idItem);
    }

    @GetMapping
    public List<ItemDto> getAllItems(@RequestHeader(USER_ID) Long idOwner) {
        log.info("method = 'GET' endpoint = '/users' function = 'get all items'");
        return itemRepository.findByOwnerId(idOwner).stream()
                .map(ItemMapper::toItemDto)
                .map(itemService::itemSetBooking)
                .map(itemService::itemSetComment)
                .sorted(Comparator.comparing(ItemDto::getId))
                .collect(Collectors.toList());
    }

    @GetMapping("/search")
    public List<ItemDto> findItemByParams(@RequestParam String text) {
        if (text.isBlank()) {
            return new ArrayList<>();
        }
        log.info("method = 'GET' endpoint = '/users/search{} function = 'find item by params'", text);
        return ItemMapper.toItemDtoList(itemService.getItemsByText(text));
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto createComment(@PathVariable Long itemId,
                                    @NotEmpty @RequestHeader(USER_ID) Long userId,
                                    @Valid @RequestBody CommentDto commentDto) {
        log.info("method = 'Post' endpoint = '/{}/comment function = 'create comment''", itemId);
        return itemService.createComment(itemId, userId, commentDto);
    }
}
