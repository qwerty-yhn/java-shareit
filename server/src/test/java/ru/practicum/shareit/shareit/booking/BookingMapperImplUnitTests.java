package ru.practicum.shareit.shareit.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingDto;
import ru.practicum.shareit.booking.BookingMapperImpl;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserDto;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class BookingMapperImplUnitTests {

    @Autowired
    BookingMapperImpl bookingMapper;
    LocalDateTime start = LocalDateTime.of(2023, 11, 1, 12, 1);
    LocalDateTime end = LocalDateTime.of(2023, 12, 1, 12, 1);
    LocalDateTime dateTime = LocalDateTime.of(2024, 12, 1, 12, 1);

    User testOwner = User.builder()
            .id(1L)
            .name("Test")
            .email("test@email.ru")
            .build();

    User testRequestor = User.builder()
            .id(2L)
            .name("Test1")
            .email("test1@email.ru")
            .build();

    User testBooker = User.builder()
            .id(3L)
            .name("Test2")
            .email("test2@email.ru")
            .build();
    UserDto testBookerDto = UserDto.builder()
            .id(3L)
            .name("Test2")
            .email("test2@email.ru")
            .build();

    ItemRequest testItemRequest = ItemRequest.builder()
            .id(1L)
            .description("test request")
            .requestor(testRequestor)
            .created(dateTime)
            .build();

    Item testItem = Item.builder()
            .id(1L)
            .name("Item")
            .owner(testOwner)
            .description("test Item")
            .available(true)
            .itemRequest(testItemRequest)
            .build();
    ItemDto testItemDto = ItemDto.builder()
            .id(1L)
            .name("Item")
            .description("test Item")
            .available(true)
            .requestId(1L)
            .build();

    Booking testBooking = Booking.builder()
            .id(1L)
            .item(testItem)
            .booker(testBooker)
            .status(BookingStatus.WAITING)
            .start(start)
            .end(end)
            .build();

    BookingDto testBookingDto = BookingDto.builder()
            .id(1L)
            .item(testItemDto)
            .booker(testBookerDto)
            .status(BookingStatus.WAITING)
            .start(start)
            .end(end)
            .build();

    @Test
    void toDTO_whenBookingNotNull_thenReturnBookingDto() {
        BookingDto actualBookingDto = bookingMapper.toDTO(testBooking);
        assertEquals(testBookingDto.getId(), actualBookingDto.getId());
    }

    @Test
    void toDTO_whenBookingNull_thenReturnNull() {
        BookingDto actualBookingDto = bookingMapper.toDTO(null);
        assertNull(actualBookingDto);
    }

}