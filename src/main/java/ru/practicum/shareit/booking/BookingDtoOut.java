package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class BookingDtoOut {

    @NotNull
    private Long itemId;

    @NotNull
    @Future
    private final LocalDateTime start;

    @NotNull
    @Future
    private final LocalDateTime end;
}
