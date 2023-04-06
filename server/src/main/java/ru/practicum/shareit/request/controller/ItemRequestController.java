package ru.practicum.shareit.request.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.util.List;

import static ru.practicum.shareit.constant.Constants.USER_ID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
@Validated
public class ItemRequestController {

    private final ItemRequestService itemRequestService;

    @PostMapping
    public ItemRequestDto create(@RequestHeader(USER_ID) Long requesterId, @RequestBody ItemRequest itemRequest) {
        return itemRequestService.createRequest(requesterId, itemRequest);
    }

    @GetMapping
    public List<ItemRequestDto> getAllOfRequester(@RequestHeader(USER_ID) Long requesterId) {

        List<ItemRequestDto> temp = itemRequestService.getAllOfRequester(requesterId);
        return temp;
    }

    @GetMapping("/all")
    public List<ItemRequestDto> getAllRequests(@RequestHeader(USER_ID) Long requesterId,
                                               @RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
                                               @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        return itemRequestService.getAllRequests(requesterId, from, size);
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto findById(@PathVariable Long requestId,
                                   @RequestHeader(USER_ID) Long userId) {
        return itemRequestService.getItemRequestById(requestId, userId);
    }
}
