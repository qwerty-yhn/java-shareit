package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import static ru.practicum.shareit.constant.Constants.USER_ID;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
@Slf4j
public class BookingController {
    private final BookingService bookingService;

    @GetMapping("/{bookingId}")
    public BookingDto getBookingById(@PathVariable @NotNull Long bookingId,
                                     @RequestHeader(USER_ID) Long userId) {
        log.info("method = 'GET' endpoint = '/bookings/{}' function = 'get booking by id'", bookingId);
        return bookingService.getBookingById(bookingId, userId);
    }

    @PostMapping
    public BookingDto create(@RequestHeader(USER_ID) @NotNull Long userId,
                             @RequestBody @NotNull @Valid BookingDtoOut bookingDtoRequest) {
        log.info("method = 'POST' endpoint = '/bookings ' function = ' create booking'");
        return bookingService.create(userId, bookingDtoRequest);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto approve(@PathVariable Long bookingId,
                              @RequestHeader(USER_ID) Long userId,
                              @RequestParam @NotNull Boolean approved) {
        log.info("method = 'Patch' endpoint = '/bookings ' function = ' approve booking of state'");
        return bookingService.approve(bookingId, userId, approved);
    }

    @GetMapping
    public List<BookingDto> getAll(@RequestHeader(USER_ID) Long userId,
                                   @RequestParam(value = "state", defaultValue = "ALL") BookingState state,
                                   @RequestParam(value = "from", required = false, defaultValue = "0") @PositiveOrZero Integer from,
                                   @RequestParam(value = "size", required = false, defaultValue = "10") @Positive Integer size) {

        return bookingService.getAllBookingsByUser(userId, state, from, size);
    }

    @GetMapping("/owner")
    public List<BookingDto> getUserBookings(@RequestHeader(USER_ID) @NotNull Long userId,
                                            @RequestParam(value = "state", defaultValue = "ALL") BookingState state,
                                            @RequestParam(name = "from", required = false) @PositiveOrZero Integer from,
                                            @RequestParam(name = "size", required = false) @Positive Integer size) {
        log.info("BookingController: GET /bookings/owner");
        return bookingService.getAllItemsBookingsByOwner(userId, state, from, size);
    }

}
