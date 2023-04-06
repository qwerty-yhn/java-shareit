package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookItemRequestDto;
import ru.practicum.shareit.booking.dto.BookingState;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;


import static ru.practicum.shareit.constant.Constants.USER_ID;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
@Slf4j
public class BookingController {
    private final BookingClient bookingClient;

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getBookingById(@PathVariable @NotNull Long bookingId,
                                                 @RequestHeader(USER_ID) Long userId) {
        log.info("method = 'GET' endpoint = '/bookings/{}' function = 'get booking by id'", bookingId);
        return bookingClient.getBooking(userId, bookingId);
    }

    @GetMapping
    public ResponseEntity<Object> getAll(@RequestHeader(USER_ID) Long userId,
                                         @RequestParam(value = "state", defaultValue = "ALL") BookingState state,
                                         @RequestParam(value = "from", required = false, defaultValue = "0") @PositiveOrZero Integer from,
                                         @RequestParam(value = "size", required = false, defaultValue = "10") @Positive Integer size) {
        log.info("method = 'GET' endpoint = '/bookings ' function = ' get status {}'", state);
        return bookingClient.getBookingsOfUser(userId, state, from, size);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> getUserBookings(@RequestHeader(USER_ID) @NotNull long userId,
                                                  @RequestParam(value = "state", defaultValue = "ALL") BookingState state,
                                                  @RequestParam(name = "from", required = false, defaultValue = "0") @PositiveOrZero Integer from,
                                                  @RequestParam(name = "size", required = false, defaultValue = "10") @Positive Integer size) {
        log.info("BookingController: GET /bookings/owner {}", state);
        ResponseEntity<Object> temp = bookingClient.getBookingsOfOwner(userId, state, from, size);
        return temp;
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestHeader(USER_ID) @NotNull Long userId,
                                         @RequestBody @NotNull @Valid BookItemRequestDto bookingDtoRequest) {
        log.info("method = 'POST' endpoint = '/bookings ' function = ' create booking'");
        return bookingClient.bookItem(userId, bookingDtoRequest);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> approve(@PathVariable Long bookingId,
                                          @RequestHeader(USER_ID) Long userId,
                                          @RequestParam @NotNull Boolean approved) {
        log.info("method = 'Patch' endpoint = '/bookings ' function = ' approve booking of state'");
        return bookingClient.approveBooking(bookingId, userId, approved);
    }
}


