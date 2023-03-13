package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
@Slf4j
public class BookingController {
    private final BookingService bookingService;

    @GetMapping("/{bookingId}")
    public BookingDto getBookingById(@PathVariable Long bookingId,
                                     @NotEmpty @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("method = 'GET' endpoint = '/bookings/{}' function = 'get booking by id'", bookingId);
        return bookingService.getBookingById(bookingId, userId);
    }

    @GetMapping()
    public List<BookingDto> getAllBookingsByUser(@NotEmpty @RequestHeader("X-Sharer-User-Id") Long userId,
                                                 @RequestParam(defaultValue = "ALL", required = false) String state) {
        log.info("method = 'GET' endpoint = '/bookings state = {}' function = 'get all booking by user of state'", state);
        return bookingService.getAllBookingsByUser(userId, state);
    }

    @GetMapping("/owner")
    public List<BookingDto> getAllItemsBookingsByOwner(@NotEmpty @RequestHeader("X-Sharer-User-Id") Long userId,
                                                       @RequestParam(defaultValue = "ALL", required = false) String state) {
        log.info("method = 'GET' endpoint = '/bookings state = {}' function = 'get all booking by owner of state'", state);
        return bookingService.getAllItemsBookingsByOwner(userId, state);
    }

    @PostMapping
    public BookingDto create(@NotEmpty @RequestHeader("X-Sharer-User-Id") Long userId,
                             @Valid @RequestBody BookingDtoOut bookingDtoRequest) {
        log.info("method = 'POST' endpoint = '/bookings ' function = ' create booking'");
        return bookingService.create(userId, bookingDtoRequest);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto approve(@PathVariable Long bookingId,
                              @NotEmpty @RequestHeader("X-Sharer-User-Id") Long userId,
                              @NotEmpty @RequestParam Boolean approved) {
        log.info("method = 'Patch' endpoint = '/bookings ' function = ' approve booking of state'");
        return bookingService.approve(bookingId, userId, approved);
    }
}
