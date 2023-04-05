package ru.practicum.shareit.shareit.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.practicum.shareit.request.dao.ItemRequestRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import ru.practicum.shareit.request.exeption.NotExistDescriptionExeption;
import ru.practicum.shareit.request.model.ItemRequest;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import ru.practicum.shareit.request.service.ItemRequestServiceImpl;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class ItemRequestServiceImplUnitTests {
    User testOwner = User.builder()
            .email("email@email.ru")
            .name("test Owner")
            .id(1L)
            .build();
    User testUser = User.builder()
            .email("email@email.ru")
            .name("test User")
            .id(2L)
            .build();
    Item testItem = Item.builder()
            .id(1L)
            .name("test item")
            .description("Test item of test Owner")
            .owner(testOwner)
            .available(true)
            .build();
    List<Item> items = new ArrayList<>();
    @Mock
    private ItemRequestRepository itemRequestRepository;
    @Mock
    private UserService userService;
    @Mock
    private ItemRepository itemRepository;
    @InjectMocks
    ItemRequestServiceImpl itemRequestService;

    @Test
    void addItemRequest() {
        Long userId = 2L;
        ItemRequestDto itemRequestDto = ItemRequestDto.builder()
                .description("Description")
                .build();
        ItemRequest itemRequest = ItemRequest.builder()
                .description("Description")
                .build();
        when(userService.getUser(any())).thenReturn(testUser);
        when(itemRequestRepository.save(any())).thenReturn(itemRequest);

        itemRequestService.createRequest(userId, itemRequest);

        verify(userService, times(1)).getUser(any());
        verifyNoMoreInteractions(userService);

        itemRequest.setDescription(null);
        assertThrows(NotExistDescriptionExeption.class,
                () -> itemRequestService.createRequest(userId, itemRequest));

    }

    @Test
    void getAllOfRequester() {
        User user = User.builder()
                .id(1L)
                .name("Test")
                .email("test@email.ru")
                .build();

        ItemRequest itemRequest = ItemRequest.builder()
                .id(1L)
                .description("Описание")
                .created(LocalDateTime.now())
                .requestor(user)
                .build();

        Item item = Item.builder()
                .id(1L)
                .name("Item")
                .owner(testUser)
                .description("test Item")
                .available(true)
                .itemRequest(itemRequest)
                .build();

        when(itemRequestRepository.customFindAll(any())).thenReturn(List.of(itemRequest));
        when(itemRepository.findAllByItemRequestId(any())).thenReturn(List.of(item));

        itemRequestService.getAllOfRequester(any());

        verify(itemRequestRepository, times(1)).customFindAll(any());
        verifyNoMoreInteractions(itemRequestRepository);
    }

    @Test
    void getAllRequests() {
        User user = User.builder()
                .id(1L)
                .name("Test")
                .email("test@email.ru")
                .build();

        ItemRequest itemRequest = ItemRequest.builder()
                .id(1L)
                .description("Описание")
                .created(LocalDateTime.now())
                .requestor(user)
                .build();

        Item item = Item.builder()
                .id(1L)
                .name("Item")
                .owner(testUser)
                .description("test Item")
                .available(true)
                .itemRequest(itemRequest)
                .build();

        when(itemRequestRepository.findAllByRequestorIdNot(any(), any())).thenReturn(List.of(itemRequest));
        when(itemRepository.findAllByItemRequestId(any())).thenReturn(List.of(item));

        itemRequestService.getAllRequests(1L, 1, 2);

        verify(itemRequestRepository, times(1)).findAllByRequestorIdNot(any(), any());
        verifyNoMoreInteractions(itemRequestRepository);
    }

    @Test
    void getItemRequestById() {
        User user = User.builder()
                .id(1L)
                .name("Test")
                .email("test@email.ru")
                .build();

        ItemRequest itemRequest = ItemRequest.builder()
                .id(1L)
                .description("Описание")
                .created(LocalDateTime.now())
                .requestor(user)
                .build();

        Item item = Item.builder()
                .id(1L)
                .name("Item")
                .owner(testUser)
                .description("test Item")
                .available(true)
                .itemRequest(itemRequest)
                .build();

        when(itemRequestRepository.findById(any())).thenReturn(Optional.of(itemRequest));
        when(itemRepository.findAllByItemRequestId(any())).thenReturn(List.of(item));
        when(userService.getUser(any())).thenReturn(user);

        itemRequestService.getItemRequestById(1L, 1L);

        verify(itemRequestRepository, times(1)).findById(any());
        verifyNoMoreInteractions(itemRequestRepository);
    }

}