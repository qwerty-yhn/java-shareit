package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.exeption.*;
import ru.practicum.shareit.error.BadRequestException;
import ru.practicum.shareit.error.NotFoundException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final UserService userService;
    private final ItemRepository itemRepository;
    private final ItemService itemService;

    @Transactional
    @Override
    public BookingDto getBookingById(Long bookingId, Long userId) {
        Booking booking = getBooking(bookingId);
        if (booking.getBooker().getId().equals(userId) || booking.getItem().getOwner().getId().equals(userId)) {
            return bookingMapper.toDTO(booking);
        } else {
            throw new BookingNotFoundException(bookingId);
        }
    }

    @Transactional
    @Override
    public List<BookingDto> getAllBookingsByUser(Long userId, BookingState state) {
        checkExist(userId);
        List<Booking> allUserBookings = bookingRepository.findAllByBooker_Id(userId);
        return getUserBookings(state, allUserBookings);
    }

    @Transactional
    @Override
    public List<BookingDto> getAllItemsBookingsByOwner(Long userId, BookingState state) {
        checkExist(userId);
        List<Item> userItems = itemRepository.findByOwnerId(userId);
        if (userItems.isEmpty()) {
            throw new BookingOfItemNotFoundException();
        }
        List<Booking> allBookings = bookingRepository.findAllByBooker_IdNotAndItemIn(userId, userItems);
        return getUserBookings(state, allBookings);
    }

    @Transactional
    @Override
    public BookingDto create(Long userId, BookingDtoOut bookingDtoOut) {
        if (bookingDtoOut.getEnd().isBefore(bookingDtoOut.getStart())) {
            throw new EndEqualAndBeforeStartException();
        }
        if (bookingDtoOut.getEnd().equals(bookingDtoOut.getStart())) {
            throw new EndEqualAndBeforeStartException();
        }

        User booker = userService.getUser(userId);
        Item item = itemService.getItem(bookingDtoOut.getItemId());

        if (booker.getId().equals(item.getOwner().getId())) {
            throw new BookingHaveOwnerNotFoundException(booker.getId(), item.getOwner().getId());
        }

        if (item.getAvailable()) {
            Booking booking = newBooking(bookingDtoOut, booker, item);
            return bookingMapper.toDTO(bookingRepository.save(booking));
        } else {
            throw new BookingUnavailableBadRequestException(item);
        }
    }

    @Transactional
    @Override
    public BookingDto approve(Long bookingId, Long userId, Boolean approved) {
        Booking booking = getBooking(bookingId);
        Long itemId = booking.getItem().getId();

        if (booking.getItem().getOwner().getId().equals(userId) && booking.getStatus().equals(BookingStatus.APPROVED)) {
            throw new DoubleApproveBadRequestException(booking.getId());
        }

        if (!approved && booking.getItem().getOwner().getId().equals(userId)) {
            booking.setStatus(BookingStatus.REJECTED);
            bookingRepository.update(booking.getStatus(), bookingId);

        } else if (approved && booking.getItem().getOwner().getId().equals(userId)) {
            booking.setStatus(BookingStatus.APPROVED);
            bookingRepository.update(booking.getStatus(), bookingId);

        } else {
            throw new BookingNotFoundException(bookingId);
        }
        return bookingMapper.toDTO(booking);
    }

    @Transactional
    @Override
    public Booking getBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException(bookingId));
        return booking;
    }

    private Booking newBooking(BookingDtoOut bookingDtoOut, User booker, Item item) {
        return Booking.builder()
                .start(bookingDtoOut.getStart())
                .end(bookingDtoOut.getEnd())
                .item(item)
                .booker(booker)
                .status(BookingStatus.WAITING)
                .build();
    }
    private void checkExist(Long userId) {
        userService.getUser(userId);
    }

    private List<BookingDto> getUserBookings(BookingState state, List<Booking> allBookings) {
        LocalDateTime now = LocalDateTime.now();
        switch (state) {
            case ALL:
                List<BookingDto> result = allBookings.stream()
                        .map(bookingMapper::toDTO)
                        .collect(Collectors.toList());
                result.sort(Comparator.comparing(BookingDto::getId).reversed());
                return result;

            case CURRENT:
                result = allBookings.stream()
                        .filter(x -> x.getStart().isBefore(now) && x.getEnd().isAfter(now))
                        .map(bookingMapper::toDTO)
                        .collect(Collectors.toList());
                result.sort(Comparator.comparing(BookingDto::getId));

                return result;

            case PAST:
                result = allBookings.stream()
                        .filter(x -> x.getStart().isBefore(now) && x.getEnd().isBefore(now))
                        .map(bookingMapper::toDTO)
                        .sorted(Comparator.comparing(BookingDto::getId).reversed())
                        .collect(Collectors.toList());
                result.sort(Comparator.comparing(BookingDto::getId).reversed());
                return result;

            case FUTURE:
                result = allBookings.stream()
                        .filter(x -> x.getStart().isAfter(now) && x.getEnd().isAfter(now))
                        .map(bookingMapper::toDTO)
                        .sorted(Comparator.comparing(BookingDto::getId).reversed())
                        .collect(Collectors.toList());
                result.sort(Comparator.comparing(BookingDto::getId).reversed());
                return result;

            case WAITING:
                result = allBookings.stream()
                        .filter(x -> x.getStatus().equals(BookingStatus.WAITING))
                        .map(bookingMapper::toDTO)
                        .sorted(Comparator.comparing(BookingDto::getId).reversed())
                        .collect(Collectors.toList());
                result.sort(Comparator.comparing(BookingDto::getId).reversed());
                return result;

            case REJECTED:
                result = allBookings.stream()
                        .filter(x -> x.getStatus().equals(BookingStatus.REJECTED))
                        .map(bookingMapper::toDTO)
                        .sorted(Comparator.comparing(BookingDto::getId).reversed())
                        .collect(Collectors.toList());
                result.sort(Comparator.comparing(BookingDto::getId).reversed());
                return result;

            default:
                throw new UnsupportedState(state);
        }
    }
}
