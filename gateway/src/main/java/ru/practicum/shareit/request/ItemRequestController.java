package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import static ru.practicum.shareit.constant.Constants.USER_ID;

@Controller
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemRequestController {
    private final ItemRequestClient itemRequestClient;

    @PostMapping
    public ResponseEntity<Object> create(@RequestHeader(USER_ID) long requesterId,
                                         @Valid @RequestBody ItemRequestDto itemRequestDto) {
        return itemRequestClient.addItemRequest(requesterId, itemRequestDto);
    }

    @GetMapping
    public ResponseEntity<Object> getAllOfRequester(@RequestHeader(USER_ID) long requesterId) {
        return itemRequestClient.getAllOwnItemRequests(requesterId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllRequests(@RequestHeader(USER_ID) long requesterId,
                                                            @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                            @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return itemRequestClient.getAllItemRequestsOfUsers(requesterId, from, size);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> findById(@RequestHeader(USER_ID) long requesterId,
                                                     @PathVariable Long requestId) {
        return itemRequestClient.getItemRequestById(requesterId, requestId);
    }
}
