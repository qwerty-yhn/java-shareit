package ru.practicum.shareit.booking;

import java.util.List;

public interface BookingService {
    BookingDto getBookingById(Long bookingId, Long userId);

    List<BookingDto> getAllBookingsByUser(Long userId, BookingState state, Integer from, Integer size);

    List<BookingDto> getAllItemsBookingsByOwner(Long userId, BookingState state, Integer from, Integer size);

    BookingDto create(Long userId, BookingDtoOut bookingDtoRequest);

    BookingDto approve(Long bookingId, Long userId, Boolean approved);

    Booking getBooking(Long bookingId);

}
