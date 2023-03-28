package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.practicum.shareit.booking.exeption.*;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemDto;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserDto;
import ru.practicum.shareit.user.UserService;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplUnitTests {
    User testOwner = User.builder()
            .email("email@email.ru")
            .name("test Owner")
            .id(1L)
            .build();
    UserDto testBookerDto = UserDto.builder()
            .email("email@email.ru")
            .name("test User")
            .id(2L)
            .build();
    User testBooker = User.builder()
            .email("email@email.ru")
            .name("test User")
            .id(2L)
            .build();
    ItemDto testItemDto = ItemDto.builder()
            .id(1L)
            .name("test item")
            .description("Test item of test Owner")
            .available(true)
            .build();
    Item testItem = Item.builder()
            .id(1L)
            .name("test item")
            .description("Test item of test Owner")
            .owner(testOwner)
            .available(true)
            .build();
    Booking expectedBooking = Booking.builder()
            .start(LocalDateTime.now())
            .end(LocalDateTime.now())
            .item(testItem)
            .booker(testBooker)
            .status(BookingStatus.WAITING)
            .build();
    BookingDto expectedBookingDto = BookingDto.builder()
            .start(LocalDateTime.now())
            .end(LocalDateTime.now())
            .item(testItemDto)
            .booker(testBookerDto)
            .build();

    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private BookingMapper bookingMapper;
    @Mock
    private UserService userService;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private ItemService itemService;
    @InjectMocks
    BookingServiceImpl bookingService;

    @Test
    void getBookingById() {
        Long bookingId = 1L;
        Long userId = 2L;

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(expectedBooking));
        when(bookingMapper.toDTO(any())).thenReturn(expectedBookingDto);

        BookingDto bookingDto = bookingService.getBookingById(bookingId, userId);

        assertEquals(expectedBookingDto, bookingDto);
    }

    @Test
    void getBookingById_one() {
        Long bookingId = 1L;
        Long userId = 2L;

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

        assertThrows(BookingNotFoundException.class, () -> bookingService.getBookingById(bookingId, userId));
    }

    @Test
    void getBookingById_two() {
        Long bookingId = 1L;
        Long userId = 0L;

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(expectedBooking));

        assertThrows(BookingNotFoundException.class, () -> bookingService.getBookingById(bookingId, userId));
    }

    @Test
    void getAllBookingsByUser_All() {

        Integer from = 0;
        Long userId = 0L;
        Integer page = 0;
        Integer size = 10;
        Sort sortByCreated = Sort.by(Sort.Direction.DESC, "start");
        when(bookingRepository.findByBookerId(userId, PageRequest.of(from / size, size, sortByCreated)))
                .thenReturn(Page.empty());
        when(userService.getUser(any())).thenReturn(new User());

        List<BookingDto> actual = bookingService.getAllBookingsByUser(userId, BookingState.ALL, from, size);
        assertEquals(0, actual.size());
    }

    @Test
    void getAllBookingsByUser_CURRENT() {

        Integer from = 0;
        Long userId = 0L;
        Integer page = 0;
        Integer size = 10;
        Sort sortByCreated = Sort.by(Sort.Direction.DESC, "start");
        when(bookingRepository.findByBookerId(userId, PageRequest.of(from / size, size, sortByCreated)))
                .thenReturn(Page.empty());
        when(userService.getUser(any())).thenReturn(new User());

        List<BookingDto> actual = bookingService.getAllBookingsByUser(userId, BookingState.CURRENT, from, size);
        assertEquals(0, actual.size());
    }

    @Test
    void getAllBookingsByUser_PAST() {

        Integer from = 0;
        Long userId = 0L;
        Integer page = 0;
        Integer size = 10;
        Sort sortByCreated = Sort.by(Sort.Direction.DESC, "start");
        when(bookingRepository.findByBookerId(userId, PageRequest.of(from / size, size, sortByCreated)))
                .thenReturn(Page.empty());
        when(userService.getUser(any())).thenReturn(new User());

        List<BookingDto> actual = bookingService.getAllBookingsByUser(userId, BookingState.PAST, from, size);
        assertEquals(0, actual.size());
    }

    @Test
    void getAllBookingsByUser_FUTURE() {

        Integer from = 0;
        Long userId = 0L;
        Integer page = 0;
        Integer size = 10;
        Sort sortByCreated = Sort.by(Sort.Direction.DESC, "start");
        when(bookingRepository.findByBookerId(userId, PageRequest.of(from / size, size, sortByCreated)))
                .thenReturn(Page.empty());
        when(userService.getUser(any())).thenReturn(new User());

        List<BookingDto> actual = bookingService.getAllBookingsByUser(userId, BookingState.FUTURE, from, size);
        assertEquals(0, actual.size());
    }

    @Test
    void getAllBookingsByUser_WAITING() {

        Integer from = 0;
        Long userId = 0L;
        Integer page = 0;
        Integer size = 10;
        Sort sortByCreated = Sort.by(Sort.Direction.DESC, "start");
        when(bookingRepository.findByBookerId(userId, PageRequest.of(from / size, size, sortByCreated)))
                .thenReturn(Page.empty());
        when(userService.getUser(any())).thenReturn(new User());

        List<BookingDto> actual = bookingService.getAllBookingsByUser(userId, BookingState.WAITING, from, size);
        assertEquals(0, actual.size());
    }

    @Test
    void getAllBookingsByUser_REJECTED() {

        Integer from = 0;
        Long userId = 0L;
        Integer page = 0;
        Integer size = 10;
        Sort sortByCreated = Sort.by(Sort.Direction.DESC, "start");
        when(bookingRepository.findByBookerId(userId, PageRequest.of(from / size, size, sortByCreated)))
                .thenReturn(Page.empty());
        when(userService.getUser(any())).thenReturn(new User());

        List<BookingDto> actual = bookingService.getAllBookingsByUser(userId, BookingState.REJECTED, from, size);
        assertEquals(0, actual.size());
    }

    @Test
    void getAllBookingsOfUser_Unsupported() {
        Long userId = 0L;
        Integer from = 0;
        Integer size = 10;
        Sort sortByCreated = Sort.by(Sort.Direction.DESC, "start");
        when(userService.getUser(userId)).thenReturn(testOwner);
        when(bookingRepository.findByBookerId(userId, PageRequest.of(from / size, size, sortByCreated)))
                .thenReturn(Page.empty());

        assertThrows(UnsupportedState.class,
                () -> bookingService.getAllBookingsByUser(userId, BookingState.UNSUPPORTED_STATUS, from, size));
    }

    @Test
    void getAllItemsBookingsByOwner() {
        Long userId = 0L;
        Integer from = 0;
        Integer size = 10;
        List<Item> ownerItems = new ArrayList<>();
        ownerItems.add(testItem);
        when(bookingRepository.findByItem_Id(any(), any())).thenReturn(Page.empty());
        when(userService.getUser(userId)).thenReturn(testOwner);
        when(itemRepository.findByOwnerId(any())).thenReturn(List.of(testItem));

        List<BookingDto> actual = bookingService.getAllItemsBookingsByOwner(userId, BookingState.ALL, from, size);
        assertEquals(0, actual.size());
    }

    @Test
    void getAllItemsBookingsByOwner_trows() {
        String state = "ALL";
        Long userId = 0L;
        Integer offset = 0;
        Integer limit = 10;
        List<Item> ownerItems = new ArrayList<>();
        when(userService.getUser(userId)).thenReturn(testOwner);
        when(itemRepository.findByOwnerId(userId)).thenReturn(new ArrayList<>());

        assertThrows(BookingOfItemNotFoundException.class,
                () -> bookingService.getAllItemsBookingsByOwner(userId, any(),
                        offset, limit));
    }

    @Test
    void getAllItemsBookingsByOwner_continue() {
        Long userId = 0L;
        Integer from = 0;
        Integer size = 10;
        List<Item> ownerItems = new ArrayList<>();
        ownerItems.add(testItem);
        //when(bookingRepository.findByItem_Id(any(), any())).thenReturn(Page.empty());
        when(userService.getUser(userId)).thenReturn(testOwner);
        when(itemRepository.findByOwnerId(any())).thenReturn(List.of(testItem));

        List<BookingDto> actual = bookingService.getAllItemsBookingsByOwner(userId, BookingState.ALL, null, null);
        assertEquals(0, actual.size());
    }

    @Test
    void addBooking_EndEqualAndBeforeStartException() {
        Long userId = 0L;
        BookingDtoOut bookingDtoIn = BookingDtoOut.builder()
                .start(LocalDateTime.now().plusDays(5))
                .end(LocalDateTime.now())
                .itemId(1L)
                .build();
        assertThrows(EndEqualAndBeforeStartException.class, () -> bookingService.create(userId, bookingDtoIn));
    }

    @Test
    void addBooking_EndEqualAndBeforeStartException2() {
        Long userId = 0L;
        BookingDtoOut bookingDtoIn = BookingDtoOut.builder()
                .start(LocalDateTime.now())
                .end(LocalDateTime.now())
                .itemId(1L)
                .build();
        assertThrows(EndEqualAndBeforeStartException.class, () -> bookingService.create(userId, bookingDtoIn));
    }

    @Test
    void addBooking_EndEqualAndBeforeStartException3() {
        Long userId = 0L;
        when(userService.getUser(userId)).thenReturn(testOwner);
        when(itemService.getItem(any())).thenReturn(testItem);

        BookingDtoOut bookingDtoIn = BookingDtoOut.builder()
                .start(LocalDateTime.now())
                .end(LocalDateTime.now().plusDays(5))
                .itemId(1L)
                .build();
        assertThrows(BookingHaveOwnerNotFoundException.class, () -> bookingService.create(userId, bookingDtoIn));
    }

    @Test
    void addBooking_EndEqualAndBeforeStartException4() {
        Long userId = 0L;
        User userCase = User.builder()
                .email("email@email.ru")
                .name("test User")
                .id(2L)
                .build();

        Item itemCase = Item.builder()
                .id(1L)
                .name("test item")
                .description("Test item of test Owner")
                .owner(testOwner)
                .available(false)
                .build();

        when(userService.getUser(userId)).thenReturn(userCase);
        when(itemService.getItem(any())).thenReturn(itemCase);

        BookingDtoOut bookingDtoIn = BookingDtoOut.builder()
                .start(LocalDateTime.now())
                .end(LocalDateTime.now().plusDays(5))
                .itemId(2L)
                .build();
        assertThrows(BookingUnavailableBadRequestException.class, () -> bookingService.create(userId, bookingDtoIn));
    }

    @Test
    void addBooking_EndEqualAndBeforeStartException_continue() {
        Long userId = 0L;
        User userCase = User.builder()
                .email("email@email.ru")
                .name("test User")
                .id(2L)
                .build();

        Item itemCase = Item.builder()
                .id(1L)
                .name("test item")
                .description("Test item of test Owner")
                .owner(testOwner)
                .available(true)
                .build();

        when(userService.getUser(userId)).thenReturn(userCase);
        when(itemService.getItem(any())).thenReturn(itemCase);
        when(bookingRepository.save(any())).thenReturn(expectedBooking);

        BookingDtoOut bookingDtoIn = BookingDtoOut.builder()
                .start(LocalDateTime.now())
                .end(LocalDateTime.now().plusDays(5))
                .itemId(2L)
                .build();

        BookingDto bookingDto = bookingService.create(userId, bookingDtoIn);

        Assertions.assertEquals(null, bookingDto);
    }

    @Test
    void approve_() {
        Long userId = 1L;
        Booking booking = Booking.builder()
                .id(0L)
                .start(LocalDateTime.now())
                .end(LocalDateTime.now())
                .item(testItem)
                .booker(testBooker)
                .status(BookingStatus.APPROVED)
                .build();
        when(bookingRepository.findById(any())).thenReturn(Optional.of(booking));

        assertThrows(DoubleApproveBadRequestException.class, () -> bookingService.approve(booking.getId(), userId, true));
    }

    @Test
    void approve_continue() {
        Long userId = 1L;
        Booking booking = Booking.builder()
                .id(0L)
                .start(LocalDateTime.now())
                .end(LocalDateTime.now())
                .item(testItem)
                .booker(testBooker)
                .status(BookingStatus.WAITING)
                .build();

        Booking expectedBooking = Booking.builder()
                .id(0L)
                .start(LocalDateTime.now())
                .end(LocalDateTime.now())
                .item(testItem)
                .booker(testBooker)
                .status(BookingStatus.APPROVED)
                .build();
        when(bookingRepository.findById(any())).thenReturn(Optional.of(booking));

        BookingDto updatedBooking = bookingService.approve(booking.getId(), userId, true);

        assertEquals(null, updatedBooking);
    }

    @Test
    void approve_continue2() {
        Long userId = 1L;
        Booking booking = Booking.builder()
                .id(0L)
                .start(LocalDateTime.now())
                .end(LocalDateTime.now())
                .item(testItem)
                .booker(testBooker)
                .status(BookingStatus.WAITING)
                .build();

        Booking expectedBooking = Booking.builder()
                .id(0L)
                .start(LocalDateTime.now())
                .end(LocalDateTime.now())
                .item(testItem)
                .booker(testBooker)
                .status(BookingStatus.REJECTED)
                .build();
        when(bookingRepository.findById(any())).thenReturn(Optional.of(booking));

        BookingDto updatedBooking = bookingService.approve(booking.getId(), userId, false);

        assertEquals(null, updatedBooking);
    }

    @Test
    void approve_continue3() {
        Long userId = 2L;
        Booking booking = Booking.builder()
                .id(0L)
                .start(LocalDateTime.now())
                .end(LocalDateTime.now())
                .item(testItem)
                .booker(testBooker)
                .status(BookingStatus.WAITING)
                .build();
        when(bookingRepository.findById(any())).thenReturn(Optional.of(booking));

        assertThrows(BookingNotFoundException.class, () -> bookingService.approve(booking.getId(), userId, true));
    }


}