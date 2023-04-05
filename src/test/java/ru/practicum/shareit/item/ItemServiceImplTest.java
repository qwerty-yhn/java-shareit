package ru.practicum.shareit.item;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.*;
import ru.practicum.shareit.item.exeption.ItemNotFoundByOwnerException;
import ru.practicum.shareit.item.exeption.ItemNotFoundException;
import ru.practicum.shareit.item.exeption.StatusUnsupportedException;
import ru.practicum.shareit.request.dao.ItemRequestRepository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserDto;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {

    @InjectMocks
    private ItemServiceImpl itemService;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private ItemMapper itemMapper;
    @Mock
    private UserService userService;
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private BookingShortMapper bookingShortMapper;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private CommentMapper commentMapper;
    @Mock
    private ItemRequestRepository itemRequestRepository;
    private final ItemRequest itemRequest = new ItemRequest(
            1L,
            "description",
            null,
            null,
            null);

    private final UserDto userDto = new UserDto(
            1L,
            "Igor",
            "igor@gmail.dom");

    private final User user = new User(
            1L,
            "Igor",
            "igor@gmail.dom");

    private final Item item = new Item(
            1L,
            "name",
            "description",
            true,
            user,
            null);

    private final Comment comment = new Comment(
            "Какой-то текст",
            LocalDateTime.now(),
            1L,
            item,
            user);


    private final CommentDto commentDto = new CommentDto(
            1L,
            "Igor",
            LocalDateTime.now()
                    .withNano(0),
            "Коммент"
    );

    private final ItemDto itemDto = new ItemDto(
            1L,
            new ArrayList<>(),
            null,
            null,
            "name",
            "description",
            true,
            null,
            1L);


    private final Booking booking = new Booking(
            1L,
            LocalDateTime.now(),
            LocalDateTime.now(),
            item,
            user,
            BookingStatus.WAITING);

    private final BookingDtoOut bookingOutputDto = new BookingDtoOut(1L, LocalDateTime.now(), LocalDateTime.now());


    @Test
    void createItem() {
        when(itemRepository.save(any(Item.class))).thenReturn(item);
        when(userService.getUser(0L)).thenReturn(user);
        when(itemRequestRepository.findById(anyLong())).thenReturn(Optional.of(itemRequest));

        Item createdItem = itemService.createItem(anyLong(), item, null);

        Assertions.assertNotNull(createdItem);
        Assertions.assertEquals(1, createdItem.getId());
        Assertions.assertEquals(itemDto.getName(), createdItem.getName());
        Assertions.assertEquals(itemDto.getDescription(), createdItem.getDescription());
        Assertions.assertTrue(createdItem.getAvailable());

        itemService.createItem(anyLong(), item, 1L);
        Assertions.assertNotNull(createdItem);

        verify(itemRepository, times(2)).save(any(Item.class));
        verifyNoMoreInteractions(itemRepository);
    }

    @Test
    void getByItemIdAndUserId() {
        when(bookingRepository.findAllByItem_Id(anyLong())).thenReturn(List.of(booking));
        when(commentRepository.findAllByItem_Id(anyLong())).thenReturn(List.of(comment));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));

        ItemDto itemById = itemService.getItemById(1L, 1L);

        Assertions.assertNotNull(itemById);
        Assertions.assertEquals(1, itemById.getId());
        Assertions.assertEquals(itemDto.getName(), itemById.getName());
        Assertions.assertEquals(itemDto.getDescription(), itemById.getDescription());
        Assertions.assertTrue(itemById.getAvailable());
        Assertions.assertEquals(itemDto.getOwnerId(), itemById.getOwnerId());

        itemById = itemService.getItemById(0L, 0L);
        Assertions.assertNotNull(itemById);

        verify(itemRepository, times(2)).findById(anyLong());
        verify(commentRepository, times(2)).findAllByItem_Id(anyLong());
        verify(bookingRepository, times(2)).findAllByItem_Id(anyLong());
        verifyNoMoreInteractions(itemRepository);
        verifyNoMoreInteractions(commentRepository);
        verifyNoMoreInteractions(bookingRepository);
    }

    @Test
    void updateItem() {
        final Item newItem = new Item(
                1L,
                "name1",
                "description",
                true,
                user,
                null);

        final Item newItem2 = new Item(
                1L,
                null,
                null,
                null,
                user,
                null);

        when(userService.getUser(anyLong())).thenReturn(user);
        when(itemRepository.save(any())).thenReturn(newItem);
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));

        Item updatedItem = itemService.updateItem(1L, anyLong(), newItem);

        Assertions.assertNotNull(updatedItem);
        Assertions.assertEquals(0, updatedItem.getId());
        Assertions.assertEquals(newItem.getName(), updatedItem.getName());
        Assertions.assertEquals(itemDto.getDescription(), updatedItem.getDescription());
        Assertions.assertTrue(updatedItem.getAvailable());

        Assertions.assertThrows(ItemNotFoundByOwnerException.class, () -> {
            itemService.updateItem(2L, 2L, newItem);
        });

        updatedItem = itemService.updateItem(1L, 2L, newItem2);
        Assertions.assertEquals(newItem.getName(), updatedItem.getName());
        Assertions.assertEquals(itemDto.getDescription(), updatedItem.getDescription());
        Assertions.assertTrue(updatedItem.getAvailable());


        verify(userService, times(2)).getUser(anyLong());
        verify(itemRepository, times(2)).save(any());
        verify(itemRepository, times(3)).findById(anyLong());
        verifyNoMoreInteractions(itemRepository);
    }

    @Test
    void getItem() {

        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));

        Item updatedItem = itemService.getItem(1L);

        Assertions.assertNotNull(updatedItem);
        Assertions.assertEquals(1, updatedItem.getId());
        Assertions.assertEquals(itemDto.getDescription(), updatedItem.getDescription());
        Assertions.assertTrue(updatedItem.getAvailable());

        verify(itemRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(itemRepository);


    }

    @Test()
    public void testGetItemNotFoundException() {

        when(itemRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions
                .assertThrows(ItemNotFoundException.class, () -> itemService.getItem(anyLong()));

    }

    @Test
    void createComment() {

        when(bookingRepository.findAllByItem_IdAndBooker_Id(anyLong(), anyLong()))
                .thenReturn(List.of(booking));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        CommentDto createdComment = itemService.createComment(anyLong(), anyLong(), commentDto);

        Assertions.assertNull(createdComment);

        verify(bookingRepository, times(1))
                .findAllByItem_IdAndBooker_Id(anyLong(), anyLong());
        verifyNoMoreInteractions(bookingRepository);

        verify(commentRepository, times(1)).save(any(Comment.class));
        verifyNoMoreInteractions(commentRepository);

        Assertions.assertThrows(StatusUnsupportedException.class, () -> {
            booking.setStatus(BookingStatus.CANCELED);
            booking.setStart(LocalDateTime.now().plusDays(5));
            CommentDto createdCommentTemp = itemService.createComment(anyLong(), anyLong(), commentDto);
        });


    }

    @Test
    void getItemsByText() {

        when(itemRepository.search(any())).thenReturn(List.of(item));

        List<Item> created = itemService.getItemsByText(any());

        Assertions.assertNotNull(created);

        verify(itemRepository, times(1)).search(any());
        verifyNoMoreInteractions(itemRepository);

    }

    @Test
    void commandMapper() {
        Comment comment = new Comment("comment", LocalDateTime.now(), 1L, null, null);
        CommentDto commentDto = new CommentDto(1L, "name", LocalDateTime.now(), "comment");

        Assertions.assertNotEquals(commentMapper.toDTO(comment), commentDto);

    }
}
