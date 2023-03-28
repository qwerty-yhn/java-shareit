package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.error.NotFoundException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.request.dao.ItemRequestRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestShortDto;
import ru.practicum.shareit.request.exeption.NotExistDescriptionExeption;
import ru.practicum.shareit.request.mapping.RequestMapper;
import ru.practicum.shareit.request.mapping.RequestShortMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemRequestServiceImpl implements ItemRequestService {
    private final UserService userService;

    private final ItemRequestRepository itemRequestRepository;

    private final ItemRepository itemRepository;

    @Override
    public ItemRequestDto createRequest(Long requestor, ItemRequest itemRequest) {
        if (itemRequest.getDescription() == null)
            throw new NotExistDescriptionExeption();

        itemRequest.setRequestor(userService.getUser(requestor));
        itemRequest.setCreated(LocalDateTime.now());

        return RequestMapper.toDto(itemRequestRepository.save(itemRequest));
    }

    @Override
    public List<ItemRequestDto> getAllOfRequester(Long requester) {

        userService.getUser(requester);
        List<ItemRequest> requests = itemRequestRepository.customFindAll(requester);
        List<Item> tempItem = itemRepository.findAllByItemRequestId(requester);

        List<ItemRequestShortDto> tmpItemRequestShortDto = RequestShortMapper.toDto(tempItem);
        if (!requests.isEmpty()) {
            requests.get(0).setItems(tmpItemRequestShortDto);
        }
        List<ItemRequestDto> temp = RequestMapper.toDto(requests);
        return temp;
    }

    @Override
    public List<ItemRequestDto> getAllRequests(Long requestorId, Integer from, Integer size) {

        List<ItemRequest> tempitemRequest = itemRequestRepository.findAllByRequestorIdNot(requestorId, PageRequest.of(from, size));

        List<ItemRequest> temp2 = new ArrayList<>();
        for (ItemRequest itemRequest : tempitemRequest) {
            itemRequest.setItems(RequestShortMapper.toDto(itemRepository.findAllByItemRequestId(itemRequest.getRequestor().getId())));
            temp2.add(itemRequest);
        }
        return RequestMapper.toDto(temp2);
    }

    @Override
    public ItemRequestDto getItemRequestById(Long requestId, Long userId) {
        userService.getUser(userId);
        ItemRequest temp = itemRequestRepository.findById(requestId).orElseThrow(() -> new NotFoundException(""));
        temp.setItems(RequestShortMapper.toDto(itemRepository.findAllByItemRequestId(temp.getRequestor().getId())));

        return RequestMapper.toDto(temp);
    }
}