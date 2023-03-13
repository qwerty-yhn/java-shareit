package ru.practicum.shareit.booking;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookingService {
    BookingDto getBookingById(Long bookingId, Long userId);

    List<BookingDto> getAllBookingsByUser(Long userId, String state);

    List<BookingDto> getAllItemsBookingsByOwner(Long userId, String state);

    BookingDto create(Long userId, BookingDtoOut bookingDtoRequest);

    BookingDto approve(Long bookingId, Long userId, Boolean approved);

    Booking getBooking(Long bookingId);
}
