package ru.practicum.shareit.request.service;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.List;

@Service
public interface ItemRequestService {

    ItemRequestDto createRequest(Long requester, ItemRequest itemRequest);

    List<ItemRequestDto> getAllOfRequester(Long requester);

    List<ItemRequestDto> getAllRequests(Long requesterId, Integer from, Integer size);

    ItemRequestDto getItemRequestById(Long userId, Long requestId);
}